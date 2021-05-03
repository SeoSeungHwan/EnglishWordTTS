package com.example.englishwordtts

import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.englishwordtts.model.Word

class WordRecyclerViewAdapter(private val dataSet : List<Word>) :
    RecyclerView.Adapter<WordRecyclerViewAdapter.ViewHolder>() {

    //TODO : 체크 박스 선택 시 선 긋고 아래로 내리기
    interface ItemChange{
        fun onChange(buttonView : CompoundButton, isChecked: Boolean, word :Word)
    }
    var itemChange : ItemChange? = null

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

        //true인건 체크박스가 체크되어있게 함
        holder.word_cb.setOnCheckedChangeListener(null)
        Log.d(TAG, "sibal id :" +dataSet[position].wordId)
        holder.word_cb.isChecked = dataSet[position].isRememberCheck == true

        fun checkboxStyleChange(){
            if(holder.word_cb.isChecked ==true){
                holder.englishName_tv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.englishName_tv.setTextColor(Color.GRAY)
                holder.koreanName_tv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.koreanName_tv.setTextColor(Color.GRAY)
            }else{
                holder.englishName_tv.paintFlags =0
                holder.koreanName_tv.paintFlags = 0
                holder.englishName_tv.setTextColor(Color.WHITE)
                holder.koreanName_tv.setTextColor(Color.WHITE)
            }
        }

        //체크박스가 체크되어있으면 글씨에 선을 그어 줌
        checkboxStyleChange()

        if(itemChange != null){
            holder.word_cb.setOnCheckedChangeListener { buttonView, isChecked ->
                itemChange?.onChange(buttonView,isChecked , dataSet[position])
                checkboxStyleChange()
            }
        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


}