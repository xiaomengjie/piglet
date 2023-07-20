package com.example.xiao.piglet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xiao.piglet.R
import com.example.xiao.piglet.bean.Password

class TotalPasswordAdapter(
    private val passwords: List<Password>,
    private val clickListener: ((Int) -> Unit)? = null): RecyclerView.Adapter<com.example.xiao.piglet.adapter.TotalPasswordAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById<TextView>(R.id.tv_name)
        val content: TextView = view.findViewById<TextView>(R.id.tv_content)
        init {
            view.setOnClickListener {
                clickListener?.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalPasswordAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_total_password, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return passwords.size
    }

    override fun onBindViewHolder(holder: TotalPasswordAdapter.ViewHolder, position: Int) {
        val data = passwords[position]
        holder.name.text = data.name
        holder.content.text = data.content
    }
}