package com.example.xiao.piglet

import com.example.xiao.piglet.ui.password.IncreasePasswordFragment
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun password(){
        val regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$".toRegex()
        for (i in 0..100) {
            val result = CharArray(12){
                IncreasePasswordFragment.sourceData[IncreasePasswordFragment.sourceData.indices.random()]
            }
            println("${result.concatToString()}      ${regex.matches(result.concatToString())}         ${checkPasswordSafety(result)}")
        }

    }

    private fun checkPasswordSafety(charArray: CharArray): Boolean{
        //48 - 57
        //65 - 90
        //97 - 122
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
}