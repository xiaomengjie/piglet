package com.example.xiao.piglet

import com.example.xiao.piglet.bean.CharacterCoefficient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Test
import java.io.File

class GenShinUnitTest {

    @Test
    fun character(){
        val gson = Gson()
        val bufferedReader = File("./characters.json").apply {
            println(absolutePath)
        }.bufferedReader()
        (gson.fromJson(
            bufferedReader,
            object : TypeToken<MutableMap<String, CharacterCoefficient>>() {}.type
        ) as MutableMap<String, CharacterCoefficient>).filter {
            it.value.isValid
        }.entries.drop(7).apply {
            forEach {
                println("${it.key}       ${it.value}")
            }
            map {
                it.key to mapOf<String, Double>(
                    "暴击率" to it.value.critical_chance,
                    "暴击伤害" to it.value.critical_hit,
                    "攻击力百分比" to it.value.atk,
                    "生命值百分比" to it.value.hp,
                    "防御力百分比" to it.value.defence,
                    "攻击力" to it.value.atk,
                    "生命值" to it.value.hp,
                    "防御力" to it.value.defence,
                    "元素精通" to it.value.element_mastery,
                    "元素充能效率" to it.value.element_charging
                )
            }
        }

    }

    @Test
    fun characterJson(){
        println(Gson().toJson(
            mutableMapOf<String, CharacterCoefficient?>("常规主C-攻暴" to CharacterCoefficient(
            0.0, 0.75, 0.0, 1.0, 1.0, 0.0, 0.0
        ), "----请选择角色----" to null)))
    }
}