package com.example.englishwordtts

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
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

    private var adapter: DateWordRecyclerViewAdapter? = null
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModel.Factory(application)
        ).get(MainViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RecyclerView의 LayoutManager
        val layoutManager = LinearLayoutManager(
            this@MainActivity,
            RecyclerView.VERTICAL,
            false
        )

        //날짜별 단어장 목록 가져오고 Livedata로 변화를 관찰
        viewModel.getAllDate()
        viewModel.dateListMutableLiveData.observe(this, androidx.lifecycle.Observer {
            adapter = DateWordRecyclerViewAdapter(it)
            dateword_rv.layoutManager = layoutManager
            dateword_rv.adapter = adapter
            adapter?.itemClick = object : DateWordRecyclerViewAdapter.ItemClick {
                override fun onClick(view: View, position: Int) {
                    val intent = Intent(this@MainActivity, WordList::class.java)
                    intent.putExtra("date", view.date_tv.text.toString())
                    startActivity(intent)
                }
            }
        })


        //오늘 단어 추가 클릭시 오늘날짜 DB에 저장
        today_add_btn.setOnClickListener {
            viewModel.getDateByName(LocalDate.now().toString())
            val intent = Intent(this, WordList::class.java)
            intent.putExtra("date", LocalDate.now().toString())
            startActivity(intent)
        }

    }
}