package com.example.xiao.piglet.tool

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

object PasswordGenerator {

    private val lowerCaseLetters = 'a' .. 'z'
    private val upperCaseLetters = 'A' .. 'Z'
    private val numbers = '0' .. '9'
    private val specialCharacters = listOf('!', '@', '#', '$', '%', '^', '&', '*', '-', '+', '=', '<', '>', '?')

    suspend fun generatePasswordContent(passwordLength: Int): String =
        withContext(Dispatchers.IO){
            val source = lowerCaseLetters + upperCaseLetters + numbers + specialCharacters
            val result = ArrayList<Char>(passwordLength)
            do {
                result.clear()
                (1..passwordLength).map{
                    Random.nextInt(0, source.size)
                }.mapTo(result) {
                    source[it]
                }
            }while (!checkPasswordSafety(result))
            result.joinToString("")
        }

    private fun checkPasswordSafety(charArray: List<Char>): Boolean{
        var containsLowerCase = false
        var containsUpperCase = false
        var containsNumber = false
        var containsSpecialCharacter = false
        charArray.forEach {
            when (it) {
                in lowerCaseLetters -> {
                    containsLowerCase = true
                }
                in upperCaseLetters -> {
                    containsUpperCase = true
                }
                in numbers -> {
                    containsNumber = true
                }
                in specialCharacters -> {
                    containsSpecialCharacter = true
                }
            }
        }
        return containsLowerCase && containsUpperCase && containsNumber && containsSpecialCharacter
    }
}