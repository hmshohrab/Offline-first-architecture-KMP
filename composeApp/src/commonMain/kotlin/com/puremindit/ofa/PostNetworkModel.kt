package com.puremindit.ofa

import io.ktor.util.Platform
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class PostNetworkModel(
    val id: Int,
    val creatorId: Int,
    val caption: String?,
    val createdAt: LocalDateTime,
    val likesCount: Long,
    val commentsCount: Long,
    val sharesCount: Long,
    val viewsCount: Long,
    val isSponsored: Boolean,
    val locationName: String?,
    val coverUrl: String,
    val isFavoritedByCurrentUser: Boolean
)
