package com.puremindit.ofa.data.db

import androidx.room.RoomDatabase


expect class Factory() {

    fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

}
internal const val dbFileName = "Offline-Social.db"
