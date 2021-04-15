package com.example.englishwordtts

import android.app.Application
import androidx.lifecycle.*
import com.example.englishwordtts.database.AppDatabase
import com.example.englishwordtts.model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//TODO WordList.kt 에서 application 인수 전달 시 null값뜨는 오류 해결하기
class WordListViewModel(application: Application) : AndroidViewModel(application) {
    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return WordListViewModel(application) as T
        }
    }
    private val context = getApplication<Application>().applicationContext
    private var appDatabase = AppDatabase.getInstance(context)?.getWordDao()

    val wordListMutableLiveData = MutableLiveData<List<Word>>()
    var words = ArrayList<Word>()


    fun getAllWordList(date : String){
        CoroutineScope(Dispatchers.IO).launch {
            words = appDatabase?.getWords(date) as ArrayList<Word>
            wordListMutableLiveData.postValue(words)
        }
    }

    fun insertWord(date: String,englishName : String, koreanName : String, isRememberCheck : Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            val parentDate = appDatabase?.getDateByName("$date")
            val newWord = Word(
                parentDate = date,
                englishName = englishName,
                koreanName = koreanName,
                isRememberCheck = isRememberCheck
            )
            appDatabase?.insertWord(newWord)
        }
    }

}