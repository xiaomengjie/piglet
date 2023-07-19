package com.example.www.saveps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.www.saveps.R
import com.example.www.saveps.bean.Password

class TotalPasswordAdapter(
    private val passwords: List<Password>,
    private val clickListener: ((Int) -> Unit)? = null): RecyclerView.Adapter<TotalPasswordAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById<TextView>(R.id.tv_name)
        val content: TextView = view.findViewById<TextView>(R.id.tv_content)
        init {
            view.setOnClickListener {
                clickListener?.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_total_password, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return passwords.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = passwords[position]
        holder.name.text = data.name
        holder.content.text = data.content
    }
}