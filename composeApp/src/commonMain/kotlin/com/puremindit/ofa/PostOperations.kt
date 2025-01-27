package com.puremindit.ofa

import com.raedghazal.kotlinx_datetime_ext.now
import io.ktor.client.HttpClient
import kotlinx.datetime.LocalDateTime

interface PostOperations {
    suspend fun getPost(id: Int): PostNetworkModel
    suspend fun updatePost(post: PostNetworkModel): Boolean
}

interface TrailsApi : PostOperations

class RealPostOperations(
    private val httpClient: HttpClient
) : PostOperations {
    override suspend fun getPost(id: Int): PostNetworkModel {
        return PostNetworkModel(
            id = id, creatorId = 1,
            caption = "caption",
            createdAt = LocalDateTime.now(),
            likesCount = 1,
            commentsCount = 2,
            sharesCount = 3,
            viewsCount = 4,
            isSponsored = true,
            locationName = "imageUrl",
            coverUrl = "videoUrl",
            isFavoritedByCurrentUser = true
        )
    }

    override suspend fun updatePost(post: PostNetworkModel): Boolean {
        return true
    }

}