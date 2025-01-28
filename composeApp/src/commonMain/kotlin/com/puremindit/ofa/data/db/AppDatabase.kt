package com.puremindit.ofa.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.puremindit.ofa.PostEntity
import com.puremindit.ofa.PostFailedDeleteEntity
import com.puremindit.ofa.PostFailedUpdateEntity
import com.puremindit.ofa.data.LocalDateConverter
import com.puremindit.ofa.data.LocalDateTimeConverter
import com.puremindit.ofa.data.db.dao.PostDao
import com.puremindit.ofa.data.db.dao.PostFailedDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@ConstructedBy(AppDatabaseConstructor::class)
@Database(
    entities = [PostEntity::class, PostFailedUpdateEntity::class, PostFailedDeleteEntity::class],
    version = 1
)
@TypeConverters(
    LocalDateTimeConverter::class, LocalDateConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun postFailedDao(): PostFailedDao


}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}


fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder.setDriver(BundledSQLiteDriver()).setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
