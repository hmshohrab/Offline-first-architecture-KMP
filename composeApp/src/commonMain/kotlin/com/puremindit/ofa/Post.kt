package com.puremindit.ofa

import kotlinx.datetime.LocalDateTime

data class Post(
    val id: Int,
    val creatorId: Int,
    val caption: String?,
    val createdAt: String,
    val likesCount: Long,
    val commentsCount: Long,
    val sharesCount: Long,
    val viewsCount: Long,
    val isSponsored: Boolean,
    val coverURL: String,
    val locationName: String?,
    val isFavoritedByCurrentUser: Boolean
)