package com.example.englishwordtts.model

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class DateWord(
    @PrimaryKey val count: Int,
    @ColumnInfo(name = "korean") val korean: String?,
    @ColumnInfo(name = "english") val english: String?,
    @ColumnInfo(name="isCheck") val isCheck: Boolean?
)