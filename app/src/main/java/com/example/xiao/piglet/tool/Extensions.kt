package com.example.xiao.piglet.tool

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import java.util.Base64
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

@RequiresApi(Build.VERSION_CODES.O)
fun String.aesEncrypt(aesKey: String): String{
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    val secretKeySpec = SecretKeySpec(aesKey.toByteArray(), "AES")
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
    val bytes = cipher.doFinal(this.toByteArray())
    return Base64.getEncoder().encodeToString(bytes)
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.aesDecrypt(aesKey: String): String{
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    val secretKeySpec = SecretKeySpec(aesKey.toByteArray(), "AES")
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
    val bytes = cipher.doFinal(Base64.getDecoder().decode(this))
    return bytes.decodeToString()
}

