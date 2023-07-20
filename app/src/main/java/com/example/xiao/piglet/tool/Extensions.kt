package com.example.xiao.piglet.tool

import kotlinx.coroutines.CoroutineScope

fun String.md5(): String = MessageDigestUtil.md5(this)

fun String.sha256(): String = MessageDigestUtil.sha256(this)

fun String.isChinese() = this.length != this.toByteArray().size

fun List<String>.toText(): String{
    return StringBuilder().apply {
        this@toText.forEach {
            append(it)
            append("\n")
        }
    }.toString()
}

suspend fun CoroutineScope.exception(action: suspend CoroutineScope.() -> Unit){
    try {
        action.invoke(this)
    }catch (e: Exception){
        e.printStackTrace()
    }
}
