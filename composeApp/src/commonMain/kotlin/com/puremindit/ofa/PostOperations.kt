package com.puremindit.ofa

import com.raedghazal.kotlinx_datetime_ext.now
import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.ktor.getApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.isSuccess
import kotlinx.datetime.LocalDateTime

interface PostOperations {
    suspend fun getPost(id: Int): PostNetworkModel?
    suspend fun updatePost(post: PostNetworkModel): Boolean
}

interface TrailsApi : PostOperations

class RealPostOperations(
    private val client: HttpClient
) : PostOperations {
    override suspend fun getPost(id: Int): PostNetworkModel? {
        val data = client.getApiResponse<PostNetworkModel> {
            url("$baseUrl/post/$id")
        }
        return data.getOrNull()
    }

    override suspend fun updatePost(post: PostNetworkModel): Boolean {
        val data = client.put {
            url("$baseUrl/post/${post.id}")
            setBody(post)
        }
        return data.status.isSuccess()
    }

}