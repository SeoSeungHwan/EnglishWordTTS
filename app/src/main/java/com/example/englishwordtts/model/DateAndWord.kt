package com.example.englishwordtts.model

import androidx.room.*


@Entity(tableName = "dates")
data class DateList(
    @PrimaryKey val date: String,
    val count: Int?
)

@Entity(
    tableName = "words",
    foreignKeys = [
    ForeignKey(
        entity = DateList::class,
        parentColumns = arrayOf("date"),
        childColumns = arrayOf("parentDate"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Word(
    @PrimaryKey(autoGenerate = true)
    val wordId: Int =0,
    val parentDate: String?,
    val englishName: String?,
    val koreanName:String?,
    var isRememberCheck:Boolean?
)


data class DateAndWord(
    @Embedded val dateList: DateList,
    @Relation(
        parentColumn = "date",
        entityColumn = "parentDate"
    )
    val wordList: List<Word>
)
