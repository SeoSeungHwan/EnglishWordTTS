package com.example.englishwordtts.model

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class DateWord(
    @PrimaryKey val date: LocalDate,
    @ColumnInfo(name = "words") val words: ArrayList<Word>?,
    @ColumnInfo(name = "playCount") val playCount: Int
)