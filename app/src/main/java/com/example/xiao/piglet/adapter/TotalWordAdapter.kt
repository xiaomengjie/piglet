package com.example.xiao.piglet.adapter

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xiao.piglet.R
import com.example.xiao.piglet.bean.Word

class TotalWordAdapter(
    private val passwords: List<Word>
): RecyclerView.Adapter<TotalWordAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val english: TextView = view.findViewById(R.id.tv_english)
        val chinese: TextView = view.findViewById(R.id.tv_chinese)
        val ukPhonetic: TextView = view.findViewById(R.id.tv_uk_phonetic)
        val usPhonetic: TextView = view.findViewById(R.id.tv_us_phonetic)
        private val usSpeech: ImageView = view.findViewById(R.id.iv_uk_speech)
        private val ukSpeech: ImageView = view.findViewById(R.id.iv_us_speech)

        init {
            ukSpeech.setOnClickListener {
                playSpeech(adapterPosition)
            }
            usSpeech.setOnClickListener {
                playSpeech(adapterPosition)
            }
        }
    }

    private val mediaPlayer by lazy { MediaPlayer() }
    private fun playSpeech(adapterPosition: Int){
        mediaPlayer.reset()
        mediaPlayer.setDataSource(passwords[adapterPosition].ukSpeech)
        mediaPlayer.prepare()
        mediaPlayer.start()
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
        holder.chinese.text = data.chinese.dropLast(1)
        holder.ukPhonetic.text = "英${data.ukPhonetic}"
        holder.usPhonetic.text = "美${data.usPhonetic}"
    }
}