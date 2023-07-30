package com.example.xiao.piglet

import com.example.xiao.piglet.tool.PasswordGenerator
import kotlinx.coroutines.runBlocking
import org.junit.Test

class PasswordGenericUnitTest {

    @Test
    fun generic(){
        runBlocking {
            (1 .. 10).forEach { _ ->
                val result = PasswordGenerator.generatePasswordContent(18)
                println("密码 = $result")
            }
        }
    }
}