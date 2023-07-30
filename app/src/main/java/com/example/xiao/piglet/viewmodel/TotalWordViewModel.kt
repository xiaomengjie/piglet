package com.example.xiao.piglet.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xiao.piglet.bean.Word
import com.example.xiao.piglet.network.NetworkClient
import com.example.xiao.piglet.network.api.WordAPI
import com.example.xiao.piglet.tool.exceptionWithDialog
import kotlinx.coroutines.launch

class TotalWordViewModel: ViewModel() {

    val words: LiveData<List<Word>>
        get() = _words

    private val _words = MutableLiveData<List<Word>>()

    val responseStatus: MutableLiveData<Int> = MutableLiveData<Int>(SUCCESS)

    fun searchWords(context: Context){
        viewModelScope.launch {
            exceptionWithDialog(context, {
                responseStatus.value = ERROR
            }) {
                val wordList = NetworkClient.create<WordAPI>().queryWord().data
                wordList?.let {
                    if (it.isNotEmpty()){
                        _words.value = it
                        responseStatus.value = SUCCESS
                    }else{
                        responseStatus.value = EMPTY
                    }
                }
            }
        }
    }

    companion object{
        const val SUCCESS = 1
        const val EMPTY = 2
        const val ERROR = 3
    }
}