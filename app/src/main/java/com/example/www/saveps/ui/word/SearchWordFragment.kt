package com.example.www.saveps.ui.word

import android.os.Bundle
import android.view.*
import androidx.lifecycle.lifecycleScope
import com.example.www.saveps.R
import com.example.www.saveps.base.BaseFragment
import com.example.www.saveps.bean.Word
import com.example.www.saveps.bean.YDResponse
import com.example.www.saveps.databinding.FragmentSearchWordBinding
import com.example.www.saveps.network.NetworkClient
import com.example.www.saveps.network.api.WordAPI
import com.example.www.saveps.tool.exception
import com.example.www.saveps.tool.toText

class SearchWordFragment : BaseFragment<FragmentSearchWordBinding>() {
    override fun initViewBinding(inflater: LayoutInflater): FragmentSearchWordBinding {
        return FragmentSearchWordBinding.inflate(inflater)
    }

    private var ydResponse: YDResponse? = null

    override val inflateMenu: Boolean
        get() = true

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_search_word, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.save){
            ydResponse?.let { saveWord(it) }
            return true
        }
        return super.onMenuItemSelected(menuItem)
    }

    private fun saveWord(ydResponse: YDResponse) {
        if (ydResponse.l != "en2zh-CHS") return
        lifecycleScope.launchWhenCreated {
            exception {
                val word = Word(ydResponse.query,
                    ydResponse.basic.explains.toText(),
                    "/${ydResponse.basic.usPhonetic}/",
                    "/${ydResponse.basic.ukPhonetic}/")
                NetworkClient.create<WordAPI>().increaseWord(word).apply {
                    refreshNotification<Word>(this@SearchWordFragment, TotalWordFragment.INCREASE_WORD, data)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnSearch.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                val content = viewBinding.etSearchContent.text.toString()
                val ydResponse = NetworkClient.create<WordAPI>(NetworkClient.YD_BASE_URL).translate(content)
                this@SearchWordFragment.ydResponse = ydResponse
                viewBinding.tvPhonetic.text = if (ydResponse.l == "en2zh-CHS"){
                    "英 /${ydResponse.basic.ukPhonetic}/  美/${ydResponse.basic.usPhonetic}/"
                }else{
                    ydResponse.basic.phonetic
                }
                viewBinding.tvTranslatingResult.text = ydResponse.basic.explains.toText()
            }
        }
    }
}