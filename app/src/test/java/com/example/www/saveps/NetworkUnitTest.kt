package com.example.www.saveps

import com.example.www.saveps.bean.Password
import com.example.www.saveps.network.NetworkClient
import kotlinx.coroutines.runBlocking
import org.junit.Test

class NetworkUnitTest {
    @Test
    fun networkIncreasePassword(){
        runBlocking {
            println(
                NetworkClient.create().insertPassword(
                    Password("facebook", "afafaedsaf")
                ))
        }
    }

    @Test
    fun networkSearchPassword(){
        runBlocking {
            println(NetworkClient.create().searchPassword("facebook"))
        }
    }

    @Test
    fun networkQueryAllPassword(){
        runBlocking {
            println(NetworkClient.create().queryAllPassword())
        }
    }

    @Test
    fun networkDeleteAllPassword(){
        runBlocking {
            println(NetworkClient.create().deleteAllPassword())
        }
    }
}
