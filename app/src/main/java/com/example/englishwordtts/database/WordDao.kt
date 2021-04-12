package com.example.englishwordtts.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.englishwordtts.model.DateAndWord
import com.example.englishwordtts.model.DateList

@Dao
interface WordDao {

    @Transaction
    @Query("SELECT * FROM DateList")
    fun getDateList() : List<DateAndWord>

}