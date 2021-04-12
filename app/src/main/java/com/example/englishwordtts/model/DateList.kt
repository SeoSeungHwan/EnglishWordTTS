package com.example.englishwordtts.model

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey


@Entity
data class DateList(
    @PrimaryKey val date: String?,
    @ColumnInfo(name = "count") val count: Int?
)

@Entity
data class WordList(
    @PrimaryKey val wrodDate: String?,
    @ColumnInfo(name = "englishName") val englishName: String?,
    @ColumnInfo(name ="koreanName") val koreanName:String,
    @ColumnInfo(name="isRememverCheck") val isRememverCheck:Boolean
)
