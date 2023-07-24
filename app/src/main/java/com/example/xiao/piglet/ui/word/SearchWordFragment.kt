package com.example.xiao.piglet.ui.word

import android.os.Bundle
import android.view.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xiao.piglet.R
import com.example.xiao.piglet.adapter.TotalWordAdapter
import com.example.xiao.piglet.base.BaseFragment
import com.example.xiao.piglet.bean.Word
import com.example.xiao.piglet.bean.YDResponse
import com.example.xiao.piglet.databinding.FragmentSearchWordBinding
import com.example.xiao.piglet.network.NetworkClient
import com.example.xiao.piglet.network.api.WordAPI
import com.example.xiao.piglet.tool.exception
import com.example.xiao.piglet.tool.isChinese
import com.example.xiao.piglet.tool.toText
import com.example.xiao.piglet.tool.toast
import com.google.android.material.divider.MaterialDividerItemDecoration

class SearchWordFragment : BaseFragment<FragmentSearchWordBinding>() {
    override fun initViewBinding(inflater: LayoutInflater): FragmentSearchWordBinding {
        return FragmentSearchWordBinding.inflate(inflater)
    }

    override val inflateMenu: Boolean
        get() = true

    private val words = mutableListOf<Word>()
    private val adapter by lazy {
        TotalWordAdapter(words)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_search_word, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.save){
            saveWord()
            return true
        }
        return super.onMenuItemSelected(menuItem)
    }

    private fun saveWord() {
        lifecycleScope.launchWhenCreated {
            exception {
                NetworkClient.create<WordAPI>().increaseWord(words).apply {
                    if (code == 200)
                        refreshNotification<List<Word>>(this@SearchWordFragment, TotalWordFragment.INCREASE_WORD, words)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.recyclerView.addItemDecoration(MaterialDividerItemDecoration(requireContext(), MaterialDividerItemDecoration.VERTICAL))
        viewBinding.recyclerView.adapter = adapter

        viewBinding.btnSearch.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                val content = viewBinding.etSearchContent.text.toString()
                if (content.isEmpty()){
                    "请输入关键字".toast(requireContext())
                    return@launchWhenCreated
                }
                val response = if (content.isChinese()){
                    NetworkClient.create<WordAPI>(requireContext()).queryWord(chinese = content)
                }else{
                    NetworkClient.create<WordAPI>(requireContext()).queryWord(english = content)
                }
                response.data?.let {
                    words.clear()
                    words.addAll(it)
                    adapter.notifyItemRangeChanged(0, words.size)
                }
            }
        }
    }
}