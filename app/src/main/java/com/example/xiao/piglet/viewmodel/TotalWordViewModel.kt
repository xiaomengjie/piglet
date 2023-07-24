package com.example.xiao.piglet.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xiao.piglet.bean.Word
import com.example.xiao.piglet.network.NetworkClient
import com.example.xiao.piglet.network.api.WordAPI
import kotlinx.coroutines.launch

class TotalWordViewModel: ViewModel() {

    val words: LiveData<List<Word>>
        get() = _words

    private val _words = MutableLiveData<List<Word>>()

    fun searchWords(context: Context){
        viewModelScope.launch {
            val wordList = NetworkClient.create<WordAPI>(context).queryWord().data
            wordList?.let {
                if (it.isNotEmpty()) _words.value = it
            }
        }
    }
}