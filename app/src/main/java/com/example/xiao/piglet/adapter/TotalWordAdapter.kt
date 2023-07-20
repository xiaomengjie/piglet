package com.example.xiao.piglet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xiao.piglet.R
import com.example.xiao.piglet.bean.Word

class TotalWordAdapter(
    private val passwords: List<Word>,
    private val clickListener: ((View, Int) -> Unit)? = null): RecyclerView.Adapter<TotalWordAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val english: TextView = view.findViewById(R.id.tv_english)
        val chinese: TextView = view.findViewById(R.id.tv_chinese)
        init {
            view.setOnClickListener {
                clickListener?.invoke(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_total_word, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return passwords.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = passwords[position]
        holder.english.text = data.english
        holder.chinese.text = data.chinese
    }
}