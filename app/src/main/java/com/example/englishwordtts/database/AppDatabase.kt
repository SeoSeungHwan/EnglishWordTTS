package com.example.englishwordtts.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.englishwordtts.model.DateWord

@Database(entities = arrayOf(DateWord::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}