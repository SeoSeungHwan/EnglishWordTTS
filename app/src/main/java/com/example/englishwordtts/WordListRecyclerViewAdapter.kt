package com.example.englishwordtts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.englishwordtts.model.DateList
import com.example.englishwordtts.model.Word

class WordRecyclerViewAdapter(private val dataSet : List<Word>) :
    RecyclerView.Adapter<WordRecyclerViewAdapter.ViewHolder>() {

    //TODO : 체크 박스 선택 시 선 긋고 아래로 내리기
    //TODO : 각 재생버튼 onClick 리스너 구현
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val englishName_tv : TextView
        val koreanName_tv : TextView
        val word_cb : CheckBox
        val word_play_btn : ImageButton

        init{
            englishName_tv = view.findViewById(R.id.englishname_tv)
            koreanName_tv = view.findViewById(R.id.koreanname_tv)
            word_cb = view.findViewById(R.id.word_cb)
            word_play_btn = view.findViewById(R.id.wordtts_play_btn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_list_item,parent,false)
        return ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.englishName_tv.text = dataSet[position].englishName
        holder.koreanName_tv.text = dataSet[position].koreanName


    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}