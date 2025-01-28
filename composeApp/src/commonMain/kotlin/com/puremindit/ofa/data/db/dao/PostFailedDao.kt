package com.puremindit.ofa.data.db.dao

import androidx.room.*
import com.puremindit.ofa.PostFailedDeleteEntity
import com.puremindit.ofa.PostFailedUpdateEntity

@Dao
interface PostFailedDao {

    // Insert operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFailedUpdate(postFailedUpdate: PostFailedUpdateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFailedDelete(postFailedDelete: PostFailedDeleteEntity)

    // Retrieve a single failed update or delete
    @Query("SELECT timestamp FROM postFailedUpdate WHERE post_id = :postId LIMIT 1")
    suspend fun getOneFailedUpdate(postId: Int): Long?

    @Query("SELECT timestamp FROM postFailedDelete WHERE post_id = :postId LIMIT 1")
    suspend fun getOneFailedDelete(postId: Int): Long?

    // Retrieve many failed updates or deletes for given post IDs
    @Query("SELECT timestamp FROM postFailedUpdate WHERE post_id IN (:postIds)")
    suspend fun getManyFailedUpdates(postIds: List<Int>): List<Long>

    @Query("SELECT timestamp FROM postFailedDelete WHERE post_id IN (:postIds)")
    suspend fun getManyFailedDeletes(postIds: List<Int>): List<Long>

    // Retrieve all failed updates or deletes
    @Query("SELECT timestamp FROM postFailedUpdate")
    suspend fun getFailedUpdates(): List<Long>

    @Query("SELECT timestamp FROM postFailedDelete")
    suspend fun getFailedDeletes(): List<Long>

    // Delete specific failed update or delete
    @Query("DELETE FROM postFailedUpdate WHERE post_id = :postId")
    suspend fun clearFailedUpdate(postId: Int)

    @Query("DELETE FROM postFailedDelete WHERE post_id = :postId")
    suspend fun clearFailedDelete(postId: Int)

    // Delete all failed updates or deletes
    @Query("DELETE FROM postFailedUpdate")
    suspend fun clearAllFailedUpdates()

    @Query("DELETE FROM postFailedDelete")
    suspend fun clearAllFailedDeletes()
}
