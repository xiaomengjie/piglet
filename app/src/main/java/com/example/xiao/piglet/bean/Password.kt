package com.example.xiao.piglet.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Password(
    val name: String,
    val content: String,
    val size: Int = 12
): Parcelable