package com.example.www.saveps.ui.word

import android.os.Bundle
import android.view.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.www.saveps.R
import com.example.www.saveps.adapter.TotalWordAdapter
import com.example.www.saveps.base.BaseFragment
import com.example.www.saveps.base.MessageEvent
import com.example.www.saveps.bean.Word
import com.example.www.saveps.databinding.FragmentTotalWordBinding
import com.example.www.saveps.network.NetworkClient
import com.example.www.saveps.network.api.WordAPI
import com.google.android.material.divider.MaterialDividerItemDecoration

class TotalWordFragment : BaseFragment<FragmentTotalWordBinding>() {

    override fun initViewBinding(inflater: LayoutInflater): FragmentTotalWordBinding {
        return FragmentTotalWordBinding.inflate(inflater)
    }

    override val useEventBus: Boolean
        get() = true

    override val inflateMenu: Boolean
        get() = true

    private val words: MutableList<Word> = mutableListOf()
    private val adapter by lazy {
        TotalWordAdapter(words){ view, position ->
            findNavController()
                .navigate(R.id.action_total_word_fragment_to_word_detail_fragment, Bundle().apply {
                    putString(arguments_english_word, words[position].english)
                })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.wordRecyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.wordRecyclerView.addItemDecoration(MaterialDividerItemDecoration(requireContext(), MaterialDividerItemDecoration.VERTICAL))
        viewBinding.wordRecyclerView.adapter = adapter

        lifecycleScope.launchWhenCreated {
            NetworkClient.create<WordAPI>().queryAllWord().data?.let {
                words.clear()
                words.addAll(it)
                adapter.notifyItemRangeChanged(0, words.size)
            }
        }
    }

    override fun <T> refreshDisplay(messageEvent: MessageEvent<T>) {
        when(messageEvent.code){
            INCREASE_WORD -> {
                val word = messageEvent.data as Word
                val index = words.indexOf(word)
                if (index == -1){
                    words.add(word)
                    adapter.notifyItemInserted(words.size - 1)
                }else{
                    words[index] = word
                    adapter.notifyItemChanged(index)
                }
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
        const val INCREASE_WORD = 1
        const val arguments_english_word = "arguments_english_word"
    }
}