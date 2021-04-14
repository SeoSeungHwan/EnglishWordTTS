package com.example.englishwordtts

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.englishwordtts.database.AppDatabase
import com.example.englishwordtts.model.DateList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.date_list_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "Test"
    }

    private var adapter: DateWordRecyclerViewAdapter? = null
    private val appDataBase by lazy {
        AppDatabase.getInstance(this)?.getWordDao()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RecyclerView관련
        val layoutManager = LinearLayoutManager(
            this@MainActivity,
            RecyclerView.VERTICAL,
            false
        )

        
        //RecyclerView에 날짜별 단어리스트 추가 후 onClick메서드 구현
        CoroutineScope(Dispatchers.IO).launch {
            val dates = appDataBase?.getDate()
            Log.d(TAG, "onCreate: ${dates.toString()}")
            adapter = dates?.let { DateWordRecyclerViewAdapter(it) }
            adapter?.itemClick = object : DateWordRecyclerViewAdapter.ItemClick {
                override fun onClick(view: View, position: Int) {
                    val intent = Intent(this@MainActivity, WordList::class.java)
                    intent.putExtra("date", view.date_tv.text.toString())
                    startActivity(intent)
                }
            }
            //TODO 쓰레드 겹침현상 해결하기
            dateword_rv.layoutManager = layoutManager
            dateword_rv.adapter = adapter
        }


        //오늘 단어 추가 클릭시 오늘날짜 DB에 저장
        today_add_btn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                //만약 오늘 날짜가 존재하지않으면 오늘날짜에 대한 열 삽입
                if (appDataBase?.getDateByName(LocalDate.now().toString()) == null) {
                    val newDate = DateList(date = LocalDate.now().toString(), count = 0)
                    appDataBase?.insertDate(newDate)
                }
            }
            val intent = Intent(this, WordList::class.java)
            intent.putExtra("date", LocalDate.now().toString())
            startActivity(intent)

        }

    }
}