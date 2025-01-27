package com.puremindit.ofa

import com.puremindit.ofa.PostExtensions.asNetworkModel
import com.puremindit.ofa.PostExtensions.asPostEntity
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDateTime
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
                emit(Post(1, 2, "3", LocalDateTime.parse(""), 4, 5, 6, 7, true, "8", "9", true))


            }
        },
        writer = { _, postEntity ->
            //   trailsDatabase.postQueries.insertPost(postEntity)
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
                /*       trailsDatabase.postBookkeepingQueries
                           .selectMostRecentFailedSync(id).executeAsOneOrNull()?.let { failedSync ->
                               timestampToEpochMilliseconds(timestamp = failedSync.timestamp)
                           }*/
                id.toLong()
            },
            setLastFailedSync = { id, timestamp ->
                try {
                    /*   trailsDatabase.postBookkeepingQueries.insertFailedSync(
                           PostFailedSync(
                               post_id = id,
                               timestamp = epochMillisecondsToTimestamp(timestamp)
                           )
                       )*/
                    true
                } catch (e: Exception) {
                    // Handle the exception
                    false
                }
            },
            clear = { id ->
                try {
                    // trailsDatabase.postBookkeepingQueries.clearByPostId(id)
                    true
                } catch (e: Exception) {
                    // Handle the exception
                    false
                }
            },
            clearAll = {
                try {
                    //  trailsDatabase.postBookkeepingQueries.clearAll()
                    true
                } catch (e: Exception) {
                    // Handle the exception
                    false
                }
            }
        )

}