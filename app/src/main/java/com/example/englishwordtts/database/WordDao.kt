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

    @Query("SELECT * FROM dateword WHERE date IN (:date)")
    fun loadAllByIds(date: IntArray): List<DateWord>

    @Insert
    fun insertAll(vararg dateWord: DateWord)

    @Delete
    fun delete(dateWord: DateWord)
}