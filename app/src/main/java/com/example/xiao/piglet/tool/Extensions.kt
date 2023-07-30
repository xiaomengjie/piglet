package com.example.xiao.piglet.tool

import android.content.Context
import android.content.res.Resources
import android.util.Base64
import android.util.TypedValue
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

fun String.md5(): String = MessageDigestUtil.md5(this)

fun String.sha256(): String = MessageDigestUtil.sha256(this)

fun String.isChinese() = this.length != this.toByteArray().size

fun String.toast(context: Context){
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

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

val Float.dp2px
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

/**
 * flags = Base64.NO_WRAP时
 * Base64.encodeToString(bytes, Base64.NO_WRAP) = Base64.getEncoder().encodeToString(bytes)
 * android.util.Base64 与 java.util.Base64
 *
 * 先加密，然后base64编码
 */
suspend fun String.aesEncrypt(aesKey: String) = withContext(Dispatchers.IO){
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    val secretKeySpec = SecretKeySpec(aesKey.toByteArray(), "AES")
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
    val bytes = cipher.doFinal(this@aesEncrypt.toByteArray())
    Base64.encodeToString(bytes, Base64.NO_WRAP)
//    Base64.getEncoder().encodeToString(bytes)
}

/**
 * 先base64解码，在解密
 */
suspend fun String.aesDecrypt(aesKey: String) = withContext(Dispatchers.IO){
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    val secretKeySpec = SecretKeySpec(aesKey.toByteArray(), "AES")
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
    val base64 = Base64.decode(this@aesDecrypt, Base64.NO_WRAP)
//    val base64 = Base64.getDecoder().decode(this)
    val bytes = cipher.doFinal(base64)
    bytes.decodeToString()
}

