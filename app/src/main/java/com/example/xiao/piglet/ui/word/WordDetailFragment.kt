package com.example.xiao.piglet.ui.word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xiao.piglet.adapter.TranslationResultAdapter
import com.example.xiao.piglet.base.BaseFragment
import com.example.xiao.piglet.bean.YDResponse
import com.example.xiao.piglet.databinding.FragmentWordDetailBinding
import com.example.xiao.piglet.network.NetworkClient
import com.example.xiao.piglet.network.api.WordAPI
import com.example.xiao.piglet.tool.exception

class WordDetailFragment : BaseFragment<FragmentWordDetailBinding>() {
    override fun initViewBinding(inflater: LayoutInflater): FragmentWordDetailBinding {
        return FragmentWordDetailBinding.inflate(inflater)
    }

    private val englishWord: String by lazy {
        arguments?.getString(TotalWordFragment.arguments_english_word)!!
    }

    private val chineseList = mutableListOf<String>()
    private val adapter by lazy {
        TranslationResultAdapter(chineseList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.chineseRecyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.chineseRecyclerView.adapter = adapter

        lifecycleScope.launchWhenCreated {
            exception {
                val ydResponse = NetworkClient.create<WordAPI>(NetworkClient.YD_BASE_URL)
                    .translate(englishWord)
                updateView(ydResponse)
            }
        }
    }

    private fun updateView(ydResponse: YDResponse) {
        viewBinding.tvEnglish.text = ydResponse.query
        viewBinding.tvPhonetic.text = "英 /${ydResponse.basic.ukPhonetic}/  美 /${ydResponse.basic.usPhonetic}/"
        chineseList.addAll(ydResponse.basic.explains)
        adapter.notifyItemRangeChanged(0, chineseList.size)
    }
}