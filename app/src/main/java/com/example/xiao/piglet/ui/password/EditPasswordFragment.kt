package com.example.xiao.piglet.ui.password

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.xiao.piglet.R
import com.example.xiao.piglet.base.BaseFragment
import com.example.xiao.piglet.bean.Password
import com.example.xiao.piglet.databinding.FragmentEditPasswordBinding
import com.example.xiao.piglet.network.NetworkClient
import com.example.xiao.piglet.network.api.PasswordAPI
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class EditPasswordFragment : BaseFragment<FragmentEditPasswordBinding>() {

    override fun initViewBinding(inflater: LayoutInflater): FragmentEditPasswordBinding {
        return FragmentEditPasswordBinding.inflate(inflater)
    }

    override val inflateMenu: Boolean
        get() = true

    private val password: Password by lazy {
        arguments?.getParcelable(TotalPasswordFragment.EXTRA_PASSWORD)!!
    }

    private val position: Int by lazy {
        arguments?.getInt(TotalPasswordFragment.EXTRA_PASSWORD_POSITION, -1)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.tvWhereUsed.text = password.name
        viewBinding.etContent.setText(password.content)
        viewBinding.btnConfirm.setOnClickListener {
            //更新密码
            lifecycleScope.launchWhenCreated {
                val content = viewBinding.etContent.text.toString()
                val password = password.copy(name = password.name, content = content, size = content.length)
                NetworkClient.create<PasswordAPI>().updatePassword(password)
                    .refreshNotification(this@EditPasswordFragment, Password.UPDATE_PASSWORD, password, position)
            }
        }
    }

    private fun deletePassword() {
        lifecycleScope.launchWhenCreated {
            NetworkClient.create<PasswordAPI>().deletePassword(
                password.name.toRequestBody(("text/plain".toMediaTypeOrNull()))
            ).refreshNotification<Nothing>(this@EditPasswordFragment, Password.DELETE_PASSWORD, position = position)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_edit_password, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.delete){
            deletePassword()
            return true
        }
        return super.onMenuItemSelected(menuItem)
    }
}