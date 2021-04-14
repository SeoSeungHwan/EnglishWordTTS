package com.example.englishwordtts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwordtts.database.AppDatabase
import com.example.englishwordtts.model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//TODO WordList.kt 에서 application 인수 전달 시 null값뜨는 오류 해결하기
class WordListViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    val mutableLiveData = MutableLiveData<List<Word>>()
    var words = ArrayList<Word>()
    var appDatabase = AppDatabase.getInstance(context)?.getWordDao()

    suspend fun getAllWordList(date : String){
        CoroutineScope(Dispatchers.IO).launch {
            words = appDatabase?.getWords(date) as ArrayList<Word>
            mutableLiveData.value = words
        }
    }

}