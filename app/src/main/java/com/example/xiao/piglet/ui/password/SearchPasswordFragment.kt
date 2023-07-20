package com.example.xiao.piglet.ui.password

import android.os.Bundle
import android.view.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.xiao.piglet.R
import com.example.xiao.piglet.base.BaseFragment
import com.example.xiao.piglet.databinding.FragmentSearchPasswordBinding
import com.example.xiao.piglet.network.NetworkClient
import com.example.xiao.piglet.network.api.PasswordAPI

class SearchPasswordFragment : BaseFragment<FragmentSearchPasswordBinding>() {
    override fun initViewBinding(inflater: LayoutInflater): FragmentSearchPasswordBinding {
        return FragmentSearchPasswordBinding.inflate(inflater)
    }

    override val inflateMenu: Boolean
        get() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnSearch.setOnClickListener {
            val content = viewBinding.etSearchContent.text.toString()
            if (content.isNotEmpty()){
                lifecycleScope.launchWhenCreated {
                    val result = NetworkClient.create<PasswordAPI>().searchPassword(content)
                    viewBinding.tvSearchResult.text = result.data?.content?: "还未保存${content}的密码"
                }
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_search_password, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.total){
            findNavController().navigate(R.id.action_search_password_fragment_to_total_password_fragment)
            return true
        }
        return super.onMenuItemSelected(menuItem)
    }
}