package com.puremindit.ofa

import io.ktor.util.Platform

data class PostEntity(
    val id: Long,
    val creator_id: Long,
    val caption: String?,
    val created_at: String,
    val likes_count: Long,
    val comments_count: Long,
    val shares_count: Long,
    val views_count: Long,
    val is_sponsored: Long,
    val cover_url: String,
    val location_name: String?,
    val is_favorited_by_current_user: Long
)
