package com.example.xiao.piglet.ui.password

import android.os.Bundle
import android.view.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xiao.piglet.R
import com.example.xiao.piglet.network.NetworkClient
import com.example.xiao.piglet.adapter.TotalPasswordAdapter
import com.example.xiao.piglet.base.BaseFragment
import com.example.xiao.piglet.base.MessageEvent
import com.example.xiao.piglet.bean.Password
import com.example.xiao.piglet.databinding.FragmentTotalPasswordBinding
import com.example.xiao.piglet.network.api.PasswordAPI

class TotalPasswordFragment : BaseFragment<FragmentTotalPasswordBinding>() {

    override fun initViewBinding(inflater: LayoutInflater): FragmentTotalPasswordBinding {
        return FragmentTotalPasswordBinding.inflate(inflater)
    }

    override val useEventBus: Boolean
        get() = true

    override val inflateMenu: Boolean
        get() = true

    private val passwords = mutableListOf<Password>()
    private val adapter by lazy {
        TotalPasswordAdapter(passwords) { position ->
            findNavController().navigate(
                R.id.action_total_password_fragment_to_edit_password_fragment,
                Bundle().also {
                    it.putParcelable(EXTRA_PASSWORD, passwords[position])
                    it.putInt(EXTRA_PASSWORD_POSITION, position)
                }
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.passwordList.layoutManager = LinearLayoutManager(context)
        viewBinding.passwordList.adapter = adapter

        queryAllPassword()
    }

    private fun queryAllPassword(){
        lifecycleScope.launchWhenCreated {
            val allPassword = NetworkClient.create<PasswordAPI>().queryAllPassword()
            allPassword.data?.let {
                passwords.clear()
                passwords.addAll(it)
                adapter.notifyItemRangeChanged(0, passwords.size)
            }
        }
    }

    override fun <T> refreshDisplay(messageEvent: MessageEvent<T>) {
        when(messageEvent.code){
            Password.INCREASE_PASSWORD -> {
                val password = messageEvent.data as Password
                passwords.add(password)
                adapter.notifyItemInserted(passwords.size - 1)
            }
            Password.DELETE_PASSWORD -> {
                val position = messageEvent.position
                passwords.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
            Password.UPDATE_PASSWORD -> {
                val position = messageEvent.position
                val password = messageEvent.data as Password
                passwords[position] = password
                adapter.notifyItemChanged(position)
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_total_password, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.increase){
            findNavController().navigate(
                R.id.action_total_password_fragment_to_increase_password_fragment
            )
            return true
        }
        return super.onMenuItemSelected(menuItem)
    }

    companion object{
        const val EXTRA_PASSWORD = "extra_password"
        const val EXTRA_PASSWORD_POSITION = "extra_password_position"
    }
}