package com.puremindit.ofa

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // 1
    val creatorId: Int = 0, // 101
    val caption: String? = "", // Exploring the beauty of the mountains 🏔️✨
    val createdAt: String = "", // 2025-01-25T14:30:00
    val likesCount: Long = 0, // 345
    val commentsCount: Long = 0, // 27
    val sharesCount: Long = 0, // 10
    val viewsCount: Long = 0, // 1200
    val isSponsored: Boolean = false, // false
    val coverURL: String = "", // https://example.com/images/mountain.jpg
    val locationName: String? = "", // Rocky Mountains
    val isFavoritedByCurrentUser: Boolean= false // true
)