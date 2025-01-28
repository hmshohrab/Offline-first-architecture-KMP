package com.puremindit.ofa.data.db


import androidx.room.Room
import androidx.room.RoomDatabase
import co.touchlab.kermit.Logger
import java.io.File

actual class Factory {
    private val dbFile = File(System.getProperty("java.io.tmpdir"), dbFileName)

    actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        Logger.d("dbFile: $dbFile")

        return Room.databaseBuilder<AppDatabase>(
            name = dbFile.absolutePath,
        )
    }

}