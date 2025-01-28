package com.puremindit.ofa.data.db.dao;

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.puremindit.ofa.Post
import com.puremindit.ofa.PostEntity

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(topicEntity: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(topicEntities: List<PostEntity>)

    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun selectPostById(id: Int): Post?
}