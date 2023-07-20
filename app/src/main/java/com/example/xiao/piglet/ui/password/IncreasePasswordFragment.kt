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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
            val content = generatePasswordContent(passwordLength)
            val password = Password(name, content, passwordLength)
            NetworkClient.create<PasswordAPI>().insertPassword(password)
                .refreshNotification(this@IncreasePasswordFragment, Password.INCREASE_PASSWORD, password)
        }
    }

    private suspend fun generatePasswordContent(passwordLength: Int): String =
        withContext(Dispatchers.IO){
//            val regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$".toRegex()
            val charArray = CharArray(passwordLength)
            do {
                charArray.indices.forEach {
                    charArray[it] = sourceData[sourceData.indices.random()]
                }
            }while (!checkPasswordSafety(charArray))
            charArray.concatToString()
        }

    private fun checkPasswordSafety(charArray: CharArray): Boolean{
        //48 - 57(0 - 9)
        //65 - 90(A - Z)
        //97 - 122(a - z)
        var hasFigure = false
        var hasCapital = false
        var hasLower = false
        charArray.forEach {
            if (!hasFigure) hasFigure = it.code in 48..57
            if (!hasCapital) hasCapital = it.code in 65..90
            if (!hasLower) hasLower = it.code in 97..122
            if (hasFigure && hasCapital && hasLower){
                return true
            }
        }
        return false
    }

    companion object{
        val sourceData = charArrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        )
    }
}