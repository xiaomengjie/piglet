package com.example.xiao.piglet.ui.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.xiao.piglet.base.BaseFragment
import com.example.xiao.piglet.network.NetworkClient
import com.example.xiao.piglet.bean.Password
import com.example.xiao.piglet.databinding.FragmentIncreasePasswordBinding
import com.example.xiao.piglet.network.api.PasswordAPI
import com.example.xiao.piglet.tool.PasswordGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

//生成密码并保存
class IncreasePasswordFragment : BaseFragment<FragmentIncreasePasswordBinding>() {

    override fun initViewBinding(inflater: LayoutInflater): FragmentIncreasePasswordBinding {
        return FragmentIncreasePasswordBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnConfirm.setOnClickListener {
            commitPassword()
        }
    }

    private fun commitPassword(){
        val passwordLength = viewBinding.etPasswordLength.text.toString().run {
            if (isEmpty()) 12 else toInt()
        }
        val name = viewBinding.etWhereUsed.text.toString()
        if (name.isEmpty()) return
        lifecycleScope.launchWhenCreated {
            val content = PasswordGenerator.generatePasswordContent(passwordLength)
            val password = Password(name, content, passwordLength)
            NetworkClient.create<PasswordAPI>().insertPassword(password)
                .refreshNotification(this@IncreasePasswordFragment, Password.INCREASE_PASSWORD, password)
        }
    }
}