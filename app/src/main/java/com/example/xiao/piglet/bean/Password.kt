package com.example.xiao.piglet.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Password(
    val name: String,
    val content: String,
    val size: Int = 12
): Parcelable{
    companion object{
        const val INCREASE_PASSWORD = 1
        const val DELETE_PASSWORD = 2
        const val UPDATE_PASSWORD = 3
        const val QUERY_PASSWORD = 4
    }
}