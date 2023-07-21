package com.example.xiao.piglet.ui.genshin

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import com.example.xiao.piglet.R
import com.example.xiao.piglet.tool.dp2px

class GenShinPopupWindow(context: Context): PopupWindow(context) {

    private val adapter: ArrayAdapter<String>
    private val listView: ListView
    init {
        width = 128f.dp2px.toInt()
        height = 400f.dp2px.toInt()
        contentView = View.inflate(context, R.layout.popup_window_gen_shin, null)
        listView = contentView.findViewById(R.id.listView)
        adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1)
        listView.adapter = adapter
        setBackgroundDrawable(ColorDrawable(Color.WHITE))
        isOutsideTouchable = true
        isFocusable = true
    }

    fun addAll(data: List<String>){
        adapter.clear()
        adapter.addAll(data)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        listView.onItemClickListener = onItemClickListener
    }
}