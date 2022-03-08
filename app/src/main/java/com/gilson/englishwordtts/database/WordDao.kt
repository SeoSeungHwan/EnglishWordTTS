package com.gilson.englishwordtts.database

import androidx.room.*
import com.gilson.englishwordtts.model.DateAndWord
import com.gilson.englishwordtts.model.DateList
import com.gilson.englishwordtts.model.Word

@Dao
interface WordDao {


    /*
    * 날짜 관련 Dao
    * */
    @Insert
    fun insertDate(dateList: DateList)

    @Delete
    fun deleteDate(dateList: DateList)

    @Query("SELECT * FROM dates")
    fun getDate() : List<DateList>

    @Query("SELECT * FROM dates WHERE date = :name")
    fun getDateByName(name : String) : DateList

    @Query("DELETE FROM dates WHERE date = :name")
    fun deleteDate(name : String)

    @Query("DELETE FROM dates")
    fun clearDateList()

    /*
    * 영어단어 관련 DAO
    * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word : Word)

    @Delete
    fun delete(word:Word)

    @Query("SELECT * FROM words WHERE parentDate =:parentDate")
    fun getWords(parentDate : String) : List<Word>

    @Query("DELETE FROM words WHERE wordId =:wordId")
    fun deleteWord(wordId : Int)

    @Query("DELETE FROM words")
    fun clearWords()

    @Update
    fun updateWord(word: Word)

    /*
    * 날짜와 영어관계 DAO
    * */
    @Transaction
    @Query("SELECT * FROM dates")
    fun getDateListAndWord() : List<DateAndWord>
}