package com.example.englishwordtts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.englishwordtts.database.AppDatabase
import com.example.englishwordtts.model.Word
import kotlinx.android.synthetic.main.activity_word_list.*
import kotlinx.android.synthetic.main.word_list_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordList : AppCompatActivity() {

    var date = ""
    var layoutManager : LinearLayoutManager?= null
    companion object {
        private const val TAG = "Test"
    }
    private var adapter : WordRecyclerViewAdapter? = null
    private val appDataBase = AppDatabase.getInstance(this)?.getWordDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_list)

        //선택 날짜
        date = intent.getStringExtra("date").toString()
        totay_date_tv.text = date

        //RecyclerView관련
        layoutManager = LinearLayoutManager(
            this@WordList,
            RecyclerView.VERTICAL,
            false
        )


        //TODO List<Word>불러오는 것 ViewModel로 빼기
        CoroutineScope(Dispatchers.IO).launch {
            val words = date?.let { appDataBase?.getWords(it) }
            adapter = words?.let { WordRecyclerViewAdapter(it) }
            wordlist_rv.layoutManager = layoutManager
            wordlist_rv.adapter = adapter
        }



        //단어 추가 버튼 클릭 시 Room 데이터 베이스에 저장
        add_word_btn.setOnClickListener {
            var englishName = englishname_et.text.toString()
            var koreanName =koreanname_et.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val parentDate = appDataBase?.getDateByName("$date")
                val newWord = Word(
                    parentDate = parentDate?.date,
                    englishName = englishName,
                    koreanName = koreanName,
                    isRememberCheck = false
                )
                appDataBase?.insertWord(newWord)
                //TODO 데이터 삽입후 RecyclerView 갱신 구현
            }
            clearEditText()
        }
    }

    fun clearEditText() {
        englishname_et.text = null
        koreanname_et.text = null
    }



}