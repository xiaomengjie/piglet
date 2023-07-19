package com.example.www.saveps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.www.saveps.R
import com.example.www.saveps.bean.Word

class TranslationResultAdapter(
    private val chineseList: List<String>): RecyclerView.Adapter<TranslationResultAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val wordType: TextView = view.findViewById(R.id.tv_word_type)
        val chinese: TextView = view.findViewById(R.id.tv_chinese)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_translation_result, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chineseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = chineseList[position].split(" ", limit = 2)
        if (data.size == 1){
            holder.wordType.isVisible = false
            holder.chinese.text = data[0]
        }else{
            holder.wordType.text = data[0]
            holder.chinese.text = data[1].trimStart()
        }
    }
}