package com.example.englishwordtts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.englishwordtts.model.DateList

class DateWordRecyclerViewAdapter(private val dataSet : ArrayList<DateList>) :
    RecyclerView.Adapter<DateWordRecyclerViewAdapter.ViewHolder>() {

    //TODO 각 날짜 클릭 시 단어장으로 이동하는 이벤트 구현
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val date_tv : TextView
        val count_tv : TextView
        val word_list_btn : ImageButton

        init{
            date_tv = view.findViewById(R.id.date_tv)
            count_tv = view.findViewById(R.id.count_tv)
            word_list_btn = view.findViewById(R.id.word_list_btn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.date_list_item,parent,false)
        return ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date_tv.text = dataSet[position].date
        holder.count_tv.text = dataSet[position].count.toString()


    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}