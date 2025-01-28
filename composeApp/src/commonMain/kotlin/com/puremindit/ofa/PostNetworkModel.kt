package com.puremindit.ofa

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostNetworkModel(
    val id: Int,
    val creatorId: Int,
    val caption: String?,
    val createdAt: String,
    val likesCount: Long,
    val commentsCount: Long,
    val sharesCount: Long,
    val viewsCount: Long,
    val isSponsored: Boolean,
    val locationName: String?,
    @SerialName("coverURL")
    val coverUrl: String,
    val isFavoritedByCurrentUser: Boolean
)
