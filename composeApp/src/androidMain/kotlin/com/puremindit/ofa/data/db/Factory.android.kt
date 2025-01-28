package com.puremindit.ofa.data.db


import androidx.room.Room
import androidx.room.RoomDatabase
import me.sujanpoudel.utils.contextProvider.applicationContext

actual class Factory {
    actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val dbFile = applicationContext.getDatabasePath(dbFileName)

        return Room.databaseBuilder<AppDatabase>(applicationContext, dbFile.absolutePath).allowMainThreadQueries()
    }

}