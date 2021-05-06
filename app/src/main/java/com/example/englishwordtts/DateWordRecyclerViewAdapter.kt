package com.example.englishwordtts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.englishwordtts.model.DateList

class DateWordRecyclerViewAdapter(private val dataSet : List<DateList>) :
    RecyclerView.Adapter<DateWordRecyclerViewAdapter.ViewHolder>() {


    interface ItemClick{
        fun onClick(view : View ,position: Int)
    }
    var itemClick : ItemClick? = null

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val date_tv : TextView
        val word_list_btn : ImageButton
        val date_list_item_cl : ConstraintLayout
        init{
            date_tv = view.findViewById(R.id.date_tv)
            word_list_btn = view.findViewById(R.id.word_list_btn)
            date_list_item_cl = view.findViewById(R.id.date_list_item_cl)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.date_list_item,parent,false)
        return ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date_tv.text = dataSet[position].date+" 단어장"

        if(itemClick != null){
            holder?.itemView?.setOnClickListener {
                itemClick?.onClick(it,position)
            }
        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}