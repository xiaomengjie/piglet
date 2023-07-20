package com.example.xiao.piglet

import com.example.xiao.piglet.bean.Password
import com.example.xiao.piglet.network.NetworkClient
import com.example.xiao.piglet.network.api.PasswordAPI
import kotlinx.coroutines.runBlocking
import org.junit.Test

class NetworkUnitTest {
    @Test
    fun networkIncreasePassword(){
        runBlocking {
            println(
                NetworkClient.create<PasswordAPI>().insertPassword(
                    Password("facebook", "afafaedsaf")
                ))
        }
    }

    @Test
    fun networkSearchPassword(){
        runBlocking {
            println(NetworkClient.create<PasswordAPI>().searchPassword("facebook"))
        }
    }

    @Test
    fun networkQueryAllPassword(){
        runBlocking {
            println(NetworkClient.create<PasswordAPI>().queryAllPassword())
        }
    }

    @Test
    fun networkDeleteAllPassword(){
        runBlocking {
            println(NetworkClient.create<PasswordAPI>().deleteAllPassword())
        }
    }
}
