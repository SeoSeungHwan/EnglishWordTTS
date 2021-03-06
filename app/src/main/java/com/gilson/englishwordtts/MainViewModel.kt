package com.gilson.englishwordtts

import android.app.Application
import androidx.lifecycle.*
import com.gilson.englishwordtts.database.AppDatabase
import com.gilson.englishwordtts.model.DateList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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



    fun getAllDateOldest(){
        CoroutineScope(Dispatchers.IO).launch {
            words = appDatabase.getDate() as ArrayList<DateList>
            words.sortBy { it.date }
            dateListMutableLiveData.postValue(words)
        }
    }

    fun getAllDateRecent(){
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
                appDatabase?.insertDate(newDate)
            }
            getAllDateRecent()
        }
    }

}