package com.puremindit.ofa

import com.puremindit.ofa.PostExtensions.asNetworkModel
import com.puremindit.ofa.PostExtensions.asPostEntity
import com.puremindit.ofa.data.db.dao.PostDao
import com.puremindit.ofa.data.db.dao.PostFailedDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import org.mobilenativefoundation.store.core5.ExperimentalStoreApi
import org.mobilenativefoundation.store.store5.Bookkeeper
import org.mobilenativefoundation.store.store5.Converter
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.MutableStoreBuilder
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Updater
import org.mobilenativefoundation.store.store5.UpdaterResult

@OptIn(ExperimentalStoreApi::class)
typealias PostStore = MutableStore<Int, Post>

class PostStoreFactory(
    private val client: PostOperations,
    private val postDao: PostDao,
    private val postFailedDao: PostFailedDao
) {

    @OptIn(ExperimentalStoreApi::class)
    fun create(): PostStore {
        return MutableStoreBuilder.from(
            fetcher = createFetcher(),
            sourceOfTruth = createSourceOfTruth(),
            converter = createConverter()
        ).build(
            updater = createUpdater(),
            bookkeeper = createBookkeeper()
        )
    }

    private fun createFetcher(): Fetcher<Int, PostNetworkModel> {
        return Fetcher.of { id ->
            // Fetch post from the network
            client.getPost(id) ?: throw IllegalArgumentException("Post with ID $id not found.")
        }
    }

    private fun createSourceOfTruth(): SourceOfTruth<Int, PostEntity, Post> = SourceOfTruth.of(
        reader = { id ->
            flow {
                val data = postDao.selectPostById(id)
                emit(data)
            }
        },
        writer = { _, postEntity ->
            postDao.insert(postEntity)
        }
    )

    private fun createConverter(): Converter<PostNetworkModel, PostEntity, Post> =
        Converter.Builder<PostNetworkModel, PostEntity, Post>()
            .fromOutputToLocal { post -> post.asPostEntity() }
            .fromNetworkToLocal { postNetworkModel -> postNetworkModel.asPostEntity() }
            .build()

    private fun createUpdater(): Updater<Int, Post, Boolean> =
        Updater.by(
            post = { _, updatedPost ->
                val networkModel = updatedPost.asNetworkModel()
                val success = client.updatePost(networkModel)
                if (success) {
                    UpdaterResult.Success.Typed(success)
                } else {
                    UpdaterResult.Error.Message("Something went wrong.")
                }
            }
        )


    private fun createBookkeeper(): Bookkeeper<Int> =
        Bookkeeper.by(
            getLastFailedSync = { id ->
                postFailedDao.getOneFailedUpdate(id)
            },
            setLastFailedSync = { id, timestamp ->
                try {
                    postFailedDao.insertFailedUpdate(
                        PostFailedUpdateEntity(
                            post_id = id,
                            timestamp = timestamp
                        )
                    )
                    true
                } catch (e: Exception) {
                    // Handle the exception
                    false
                }
            },
            clear = { id ->
                try {
                    postFailedDao.clearFailedDelete(id)
                    true
                } catch (e: Exception) {
                    // Handle the exception
                    false
                }
            },
            clearAll = {
                try {
                    postFailedDao.clearAllFailedDeletes()
                    true
                } catch (e: Exception) {
                    // Handle the exception
                    false
                }
            }
        )

}
