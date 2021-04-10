package com.example.englishwordtts.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.englishwordtts.model.DateWord

@Dao
interface WordDao {
    @Query("SELECT * FROM dateword")
    fun getAll(): List<DateWord>


}