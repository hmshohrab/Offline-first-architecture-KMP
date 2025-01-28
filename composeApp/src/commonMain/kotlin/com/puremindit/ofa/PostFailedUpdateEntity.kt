package com.puremindit.ofa

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "postFailedUpdate")
data class PostFailedUpdateEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Optional primary key for Room
    val post_id: Int,
    val timestamp: Long
)

@Entity(tableName = "postFailedDelete")
data class PostFailedDeleteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Optional primary key for Room
    val post_id: Int,
    val timestamp: Long
)
