package com.example.xiao.piglet.ui.word

import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xiao.piglet.R
import com.example.xiao.piglet.adapter.TotalWordAdapter
import com.example.xiao.piglet.base.BaseFragment
import com.example.xiao.piglet.base.MessageEvent
import com.example.xiao.piglet.bean.Word
import com.example.xiao.piglet.databinding.FragmentTotalWordBinding
import com.example.xiao.piglet.viewmodel.TotalWordViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration

/*
* 跳转到SearchWordFragment时，onDestroyView会调用
* 返回TotalWordFragment时，会从onCreateView调用
* */
class TotalWordFragment : BaseFragment<FragmentTotalWordBinding>() {

    override fun initViewBinding(inflater: LayoutInflater): FragmentTotalWordBinding {
        return FragmentTotalWordBinding.inflate(inflater)
    }

    override val useEventBus: Boolean
        get() = true

    override val inflateMenu: Boolean
        get() = true

    private val words: MutableList<Word> = mutableListOf()
    private val adapter by lazy { TotalWordAdapter(words) }

    private val viewModel by lazy {
        ViewModelProvider(this)[TotalWordViewModel::class.java]
    }

    override fun initView(viewBinding: FragmentTotalWordBinding) {
        super.initView(viewBinding)
        viewBinding.wordRecyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.wordRecyclerView.addItemDecoration(MaterialDividerItemDecoration(requireContext(), MaterialDividerItemDecoration.VERTICAL))
        viewBinding.wordRecyclerView.adapter = adapter

        viewModel.words.observe(viewLifecycleOwner){
            words.clear()
            words.addAll(it)
            adapter.notifyItemRangeChanged(0, words.size)
        }
        viewModel.searchWords(requireContext())
    }

    override fun <T> refreshDisplay(messageEvent: MessageEvent<T>) {
        when(messageEvent.type){
            INCREASE_WORD -> {
                val words = messageEvent.data as List<Word>
                this.words.addAll(0, words)
                adapter.notifyItemRangeInserted(0, words.size)
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_total_word, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.search){
            findNavController().navigate(
                R.id.action_total_word_fragment_to_search_word_fragment
            )
            return true
        }
        return super.onMenuItemSelected(menuItem)
    }

    companion object{
        const val INCREASE_WORD = "increase_word"
    }
}