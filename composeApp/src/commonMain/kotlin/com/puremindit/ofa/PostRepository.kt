package com.puremindit.ofa

import org.mobilenativefoundation.store.core5.ExperimentalStoreApi
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreWriteRequest
import org.mobilenativefoundation.store.store5.StoreWriteResponse
import org.mobilenativefoundation.store.store5.impl.extensions.fresh
import org.mobilenativefoundation.store.store5.impl.extensions.get

interface PostRepository {
    suspend fun getPost(id: Int): Post?
    suspend fun updatePost(
        postId: Int,
        likesCount: Long? = null,
        commentsCount: Long? = null,
        sharesCount: Long? = null,
        viewsCount: Long? = null,
        isFavoritedByCurrentUser: Boolean? = null
    ): Post
}

@OptIn(ExperimentalStoreApi::class)
class RealPostRepository(
    private val postStore: PostStore
) : PostRepository {
    override suspend fun getPost(id: Int): Post? {
        return postStore.fresh<Int, Post, Boolean>(id)
    }

    override suspend fun updatePost(
        postId: Int,
        likesCount: Long?,
        commentsCount: Long?,
        sharesCount: Long?,
        viewsCount: Long?,
        isFavoritedByCurrentUser: Boolean?
    ): Post {
        val prevPost = postStore.get<Int, Post, Boolean>(postId)

        val nextPost = prevPost.copy(
            likesCount = likesCount ?: prevPost.likesCount,
            commentsCount = commentsCount ?: prevPost.commentsCount,
            sharesCount = sharesCount ?: prevPost.sharesCount,
            viewsCount = viewsCount ?: prevPost.viewsCount,
            isFavoritedByCurrentUser = isFavoritedByCurrentUser ?: prevPost.isFavoritedByCurrentUser
        )

        val writeRequest = StoreWriteRequest.of<Int, Post, Boolean>(
            key = postId,
            value = nextPost
        )

        return when (postStore.write(writeRequest)) {
            is StoreWriteResponse.Error -> prevPost
            is StoreWriteResponse.Success -> nextPost
        }
    }
}
