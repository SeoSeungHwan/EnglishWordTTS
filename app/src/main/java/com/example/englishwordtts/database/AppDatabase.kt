package com.example.englishwordtts.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.englishwordtts.model.DateList
import com.example.englishwordtts.model.Word

@Database(
    entities = [DateList::class, Word::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getWordDao(): WordDao

    companion object {

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(RoomDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "WordDB"
                    )
                        .build()
                }
            }
            return instance!!
        }
        fun getInstance() : AppDatabase?{
            return instance
        }
    }
}

