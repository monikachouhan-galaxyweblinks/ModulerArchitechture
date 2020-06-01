package com.gwl.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class SuggestionListAdapter(val listdata: List<String>, val itmClick: SuggestionClick?) :
    RecyclerView.Adapter<SuggestionListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.suggest_item, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myListData: String = listdata[position]
        holder.bind(itmClick, myListData)
        holder.textView.text = myListData
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView
        init {
            textView = itemView.findViewById(R.id.suggestion_text) as TextView
        }
        fun bind(itmClick: SuggestionClick?, item: String) {
            itemView.setOnClickListener {
                itmClick?.onclick(item)
            }
        }
    }
}

interface SuggestionClick {
    fun onclick(item: String)
}