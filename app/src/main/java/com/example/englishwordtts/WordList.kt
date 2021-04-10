package com.example.englishwordtts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_word_list.*

class WordList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_list)

        totay_date_tv.text =intent.getStringExtra("date")
        add_word_btn.setOnClickListener {

        }
    }
}