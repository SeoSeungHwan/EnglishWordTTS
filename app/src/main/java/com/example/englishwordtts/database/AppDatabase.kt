package com.example.englishwordtts.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.englishwordtts.model.DateWord

@Database(entities = arrayOf(DateWord::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): WordDao

    companion object{
        private var instance : AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context) :AppDatabase?{
            if(instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "WordDatabase"
                ).build()
            }
            return instance
        }
    }
}