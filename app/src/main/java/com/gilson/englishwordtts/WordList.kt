package com.gilson.englishwordtts
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.CompoundButton
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gilson.englishwordtts.model.Word
import kotlinx.android.synthetic.main.activity_word_list.*
import java.util.*

class WordList : AppCompatActivity() {

    //선택한 단어장 날짜
    var date = ""

    //viewModel 선언
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            WordListViewModel.Factory(application)
        ).get(WordListViewModel::class.java)
    }

    //tts
    private var tts : TextToSpeech? =null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_list)


        //TTS Init
        tts = TextToSpeech(this) {
            if (it == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale.ENGLISH)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    return@TextToSpeech
                }
            } else {
            }
        }

        //선택 날짜
        date = intent.getStringExtra("date").toString()
        totay_date_tv.text = date

        //단어 추가 버튼 클릭 시 Room 데이터 베이스에 저장 및 단어 다시 불러오기
        add_word_btn.setOnClickListener {
            var englishName = englishname_et.text.toString()
            var koreanName = koreanname_et.text.toString()
            viewModel.insertWord(date, englishName, koreanName, false)
            clearEditText()
        }

        //해당날짜 단어장 모두 삭제
        remove_date_btn.setOnClickListener {
            viewModel.removeWordList(date)
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }

        //선택된 단어들 삭제
        select_remove_btn.setOnClickListener {
            viewModel.words.forEach {
                if (it.isRememberCheck==true){
                    viewModel.removeSelectWord(date, it.wordId)
                }
            }
        }
    }

    fun clearEditText() {
        englishname_et.text = null
        koreanname_et.text = null
    }

    @Override
    override fun onStart() {
        super.onStart()

        //RecyclerView에 사용되는 layoutManager
        var layoutManager = LinearLayoutManager(
            this@WordList,
            RecyclerView.VERTICAL,
            false
        )

        //해당날짜에 해당하는 단어들 모두 가져오고 변화를 Livedata를 사용하여 관찰
        viewModel.getAllWordList(date)
        viewModel.wordListMutableLiveData.observe(this, Observer {
            val adapter = WordRecyclerViewAdapter(it)
            wordlist_rv.layoutManager = layoutManager
            wordlist_rv.adapter = adapter

            //각 단어 체크시 체크여부 Update
            adapter?.itemChange = object : WordRecyclerViewAdapter.ItemChange {
                override fun onChange(buttonView: CompoundButton, isChecked: Boolean , word: Word) {

                    var newWord = word
                    newWord.isRememberCheck = isChecked
                    viewModel.updateCheck(newWord)
                }
            }

            //각 단어 재생클릭시 영단어 읽어주기
            adapter?.itemClick = object : WordRecyclerViewAdapter.ItemClick {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun onClick(view: View, position: Int) {
                    tts?.speak(it.get(position).englishName.toString(),TextToSpeech.QUEUE_FLUSH,null,null)
                }
            }
        })

    }
}