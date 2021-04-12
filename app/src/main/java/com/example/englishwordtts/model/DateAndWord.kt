package com.example.englishwordtts.model

import androidx.room.*

data class DateAndWord(
    @Embedded val dateList: DateList,
    @Relation(
        parentColumn = "date",
        entityColumn = "wordDate"
    )
    val wordList: List<WordList>
)
