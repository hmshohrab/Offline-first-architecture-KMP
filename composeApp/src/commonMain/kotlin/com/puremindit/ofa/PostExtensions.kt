package com.puremindit.ofa

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
    // Convert from the local database model to the domain model.

    fun PostEntity.asPost(): Post {
        return Post(
            id = this.id.toInt(),
            creatorId = this.creatorId,
            caption = this.caption,
            createdAt = this.createdAt,
            likesCount = this.likesCount,
            commentsCount = this.commentsCount,
            sharesCount = this.sharesCount,
            viewsCount = this.viewsCount,
            isSponsored = this.isSponsored,
            coverURL = this.coverURL,
            locationName = this.locationName,
            isFavoritedByCurrentUser = this.isFavoritedByCurrentUser
        )
    }


    fun Post.asPostEntity(): PostEntity {
        return PostEntity(
            id = this.id.toLong(),
            creatorId = this.creatorId,
            caption = this.caption,
            createdAt = createdAt,
            likesCount = this.likesCount,
            commentsCount = this.commentsCount,
            sharesCount = this.sharesCount,
            viewsCount = this.viewsCount,
            isSponsored = this.isSponsored,
            coverURL = this.coverURL,
            locationName = this.locationName,
            isFavoritedByCurrentUser = this.isFavoritedByCurrentUser
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
