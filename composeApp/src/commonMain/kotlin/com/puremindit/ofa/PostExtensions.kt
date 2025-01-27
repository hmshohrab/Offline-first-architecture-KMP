package com.puremindit.ofa

import kotlinx.datetime.LocalDateTime

object PostExtensions {
    // Convert from the network model to the domain model.

    fun PostNetworkModel.asPost(): Post {
        return Post(
            id = this.id,
            creatorId = this.creatorId,
            caption = this.caption,
            createdAt = this.createdAt,
            likesCount = this.likesCount,
            commentsCount = this.commentsCount,
            sharesCount = this.sharesCount,
            viewsCount = this.viewsCount,
            isSponsored = this.isSponsored,
            coverURL = this.coverUrl,
            locationName = this.locationName,
            isFavoritedByCurrentUser = this.isFavoritedByCurrentUser
        )
    }

    fun PostNetworkModel.asPostEntity(): PostEntity {
        return PostEntity(
            id = this.id.toLong(),
            creator_id = this.creatorId.toLong(),
            caption = this.caption,
            created_at = this.createdAt.toString(),
            likes_count = this.likesCount,
            comments_count = this.commentsCount,
            shares_count = this.sharesCount,
            views_count = this.viewsCount,
            is_sponsored = if (this.isSponsored) 1 else 0,
            cover_url = this.coverUrl,
            location_name = this.locationName,
            is_favorited_by_current_user = if (this.isFavoritedByCurrentUser) 1L else 0L


        )
    }
    // Convert from the local database model to the domain model.

    fun PostEntity.asPost(): Post {
        return Post(
            id = this.id.toInt(),
            creatorId = this.creator_id.toInt(),
            caption = this.caption,
            createdAt = LocalDateTime.parse(this.created_at),
            likesCount = this.likes_count.toLong(),
            commentsCount = this.comments_count.toLong(),
            sharesCount = this.shares_count.toLong(),
            viewsCount = this.views_count.toLong(),
            isSponsored = this.is_sponsored == 1L,
            coverURL = this.cover_url,
            locationName = this.location_name,
            isFavoritedByCurrentUser = this.is_favorited_by_current_user == 1L
        )
    }


    fun Post.asPostEntity(): PostEntity {
        return PostEntity(
            id = this.id.toLong(),
            creator_id = this.creatorId.toLong(),
            caption = this.caption,
            created_at = createdAt.toString(),
            likes_count = this.likesCount,
            comments_count = this.commentsCount,
            shares_count = this.sharesCount,
            views_count = this.viewsCount,
            is_sponsored = if (this.isSponsored) 1 else 0,
            cover_url = this.coverURL,
            location_name = this.locationName,
            is_favorited_by_current_user = if (this.isFavoritedByCurrentUser) 1 else 0
        )
    }

    fun Post.asNetworkModel(): PostNetworkModel {
        return PostNetworkModel(
            id = this.id,
            creatorId = this.creatorId,
            caption = this.caption,
            createdAt = createdAt,
            likesCount = this.likesCount,
            commentsCount = this.commentsCount,
            sharesCount = this.sharesCount,
            viewsCount = this.viewsCount,
            isSponsored = isSponsored,
            coverUrl = this.coverURL,
            locationName = this.locationName,
            isFavoritedByCurrentUser = this.isFavoritedByCurrentUser
        )
    }
}
