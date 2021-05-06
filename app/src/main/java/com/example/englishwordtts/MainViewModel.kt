package com.example.englishwordtts

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.englishwordtts.database.AppDatabase
import com.example.englishwordtts.model.DateList
import com.example.englishwordtts.model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import java.util.Arrays.sort
import java.util.Collections.reverseOrder
import java.util.Collections.sort
import kotlin.collections.ArrayList


//TODO WordList.kt 에서 application 인수 전달 시 null값뜨는 오류 해결하기
class MainViewModel(application: Application) : AndroidViewModel(application) {
    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(application) as T
        }
    }
    private val context = getApplication<Application>().applicationContext
    private var appDatabase = AppDatabase.getInstance(context)?.getWordDao()!!


    //날짜 리스트를 받는 LivaData
    val dateListMutableLiveData = MutableLiveData<List<DateList>>()
    var words = ArrayList<DateList>()



    fun getAllDateRecent(){
        CoroutineScope(Dispatchers.IO).launch {
            words = appDatabase.getDate() as ArrayList<DateList>
            words.sortBy { it.date }
            dateListMutableLiveData.postValue(words)
        }
    }

    fun getAllDateOldest(){
        CoroutineScope(Dispatchers.IO).launch {
            words = appDatabase.getDate() as ArrayList<DateList>
            words.sortBy { it.date }
            words.reverse()
            dateListMutableLiveData.postValue(words)
        }

    }
    
    fun getDateByName(date :String){
        CoroutineScope(Dispatchers.IO).launch {
            //만약 오늘 날짜가 존재하지않으면 오늘날짜에 대한 열 삽입
            if (appDatabase?.getDateByName(date) == null){
                val newDate = DateList(date = date, count = 0)
                val date1 = DateList("2021-05-05",count =0)
                val date2 = DateList("2022-12-05",count =0)
                val date3 = DateList("1985-03-05",count =0)
                val date4 = DateList("2081-01-33",count =0)
                appDatabase?.insertDate(newDate)
                appDatabase?.insertDate(date1)
                appDatabase?.insertDate(date2)
                appDatabase?.insertDate(date3)
                appDatabase?.insertDate(date4)
            }
            getAllDateRecent()
        }
    }

 


}