package com.example.xiao.piglet.bean

import com.google.gson.annotations.SerializedName

/**
 * 属性分数
{
'暴击率': 2,
'暴击伤害': 1,
'攻击力百分比': 1.331429,
'生命值百分比': 1.331429,
'防御力百分比': 1.066362,
'攻击力': 0.199146,
'生命值': 0.012995,
'防御力': 0.162676,
'元素精通': 0.332857,
'元素充能效率': 1.197943
}
 */

//属性权重
data class CharacterCoefficient(
    @SerializedName("生命值")
    val hp: Double = 0.0,
    @SerializedName("攻击力")
    val atk: Double = 0.0,
    @SerializedName("防御力")
    val defence: Double = 0.0,
    @SerializedName("暴击率")
    val critical_chance: Double = 0.0,
    @SerializedName("暴击伤害")
    val critical_hit: Double = 0.0,
    @SerializedName("元素精通")
    val element_mastery: Double = 0.0,
    @SerializedName("元素充能效率")
    val element_charging: Double = 0.0
){
    val isValid: Boolean
        get() = !(hp == 0.0 &&
                atk == 0.0 &&
                defence == 0.0 &&
                critical_chance == 0.0 &&
                critical_hit == 0.0 &&
                element_mastery == 0.0 &&
                element_charging == 0.0)
}