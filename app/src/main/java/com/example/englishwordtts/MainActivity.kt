package com.example.englishwordtts

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.englishwordtts.database.AppDatabase
import com.example.englishwordtts.model.DateList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "Test"
    }

    private val appDataBase by lazy{
        AppDatabase.getInstance(this)?.getWordDao()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO RecyclerView에 각 날짜별 목록 나타나게 하기
        CoroutineScope(Dispatchers.IO).launch {
            val dates = appDataBase?.getDate()
            Log.d(TAG, "onCreate: $dates")

        }

        
        //오늘 단어 추가 클릭시 오늘날짜 DB에 저장
        today_add_btn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                //만약 오늘 날짜가 존재하지않으면 오늘날짜에 대한 열 삽입
                if(appDataBase?.getDateByName(LocalDate.now().toString()) == null){
                    val newDate = DateList(date = LocalDate.now().toString(), count = 0)
                    appDataBase?.insertDate(newDate)
                }
            }
            val intent = Intent(this,WordList::class.java)
            intent.putExtra("date",LocalDate.now().toString())
            startActivity(intent)



        }

    }
}