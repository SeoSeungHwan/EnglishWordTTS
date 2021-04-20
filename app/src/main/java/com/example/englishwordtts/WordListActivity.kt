package com.example.englishwordtts

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.englishwordtts.model.Word
import kotlinx.android.synthetic.main.activity_word_list.*
import kotlinx.android.synthetic.main.date_list_item.view.*
import kotlinx.android.synthetic.main.word_list_item.*
import kotlinx.android.synthetic.main.word_list_item.view.*
import java.util.*
import kotlin.collections.ArrayList

//TODO 전체 재생눌렀을 경우 STREAM으로 만들어 모두 출력
class WordListActivity : AppCompatActivity() {

    //선택한 단어장 날짜
    var date = ""

    //선택된 체크박스 리스트
    val selectCheckBoxItem = ArrayList<Word>()
    //tts
    var tts: TextToSpeech? =null
    private var reapeatTime: String = ""
    private val handler = Handler()
    private var handlerTask : Runnable? = null


    //viewModel 선언
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            WordListViewModel.Factory(application)
        ).get(WordListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_list)

        //선택 날짜
        date = intent.getStringExtra("date").toString()
        totay_date_tv.text = date

        //RecyclerView에 사용되는 layoutManager
        var layoutManager = LinearLayoutManager(
            this@WordListActivity,
            RecyclerView.VERTICAL,
            false
        )
        //TTS Init
        tts = TextToSpeech(this) {
            if (it == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale.KOREA)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    return@TextToSpeech
                }
            } else {
            }
        }

        //해당날짜에 해당하는 단어들 모두 가져오고 변화를 Livedata를 사용하여 관찰
        viewModel.getAllWordList(date)
        viewModel.wordListMutableLiveData.observe(this, Observer {
            val adapter = WordListRecyclerViewAdapter(it)
            wordlist_rv.layoutManager = layoutManager
            wordlist_rv.adapter = adapter

            adapter?.itemClick = object : WordListRecyclerViewAdapter.ItemClick {
                override fun onClick(view: View, position: Int) {
                    //TODO 각 재생버튼 클릭시 재생버튼 추가
                }
            }
        })


        //단어 추가 버튼 클릭 시 Room 데이터 베이스에 저장 및 단어 다시 불러오기
        add_word_btn.setOnClickListener {
            var englishName = englishname_et.text.toString()
            var koreanName = koreanname_et.text.toString()
            viewModel.insertWord(date, englishName, koreanName, false)
            clearEditText()
        }

        //단어장 삭제 버튼 이벤트
        remove_date_btn.setOnClickListener {
            viewModel.deleteDate(date)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)

        }

        //선택한 단어들만 삭제하는 이벤트
        select_remove_btn.setOnClickListener {

        }
    }

    fun clearEditText() {
        englishname_et.text = null
        koreanname_et.text = null
    }

}