package com.example.englishwordtts.database

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.englishwordtts.R
import com.example.englishwordtts.model.DateWord
import kotlinx.android.synthetic.main.date_list_item.view.*

class DateWordRecyclerViewAdapter(private val dataSet : ArrayList<DateWord>) :
    RecyclerView.Adapter<DateWordRecyclerViewAdapter.ViewHolder>() {

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
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date_tv.text = dataSet[position].date.toString()
        holder.count_tv.text = dataSet[position].words?.size.toString()


    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}