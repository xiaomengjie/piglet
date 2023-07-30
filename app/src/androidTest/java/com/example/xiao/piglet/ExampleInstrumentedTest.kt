package com.example.xiao.piglet

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.util.Base64

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.xiao.saveps", appContext.packageName)
    }

    @Test
    fun base64(){
//        val source = "lK!JK2+#MN9X"
//        println("source = $source")
//        val encoderString =
//            android.util.Base64.encodeToString(source.toByteArray(), android.util.Base64.DEFAULT)
//        println("encoderString = $encoderString")
//        val decodeString = Base64.getDecoder().decode(encoderString)
//        println("decodeString = $decodeString")

        val originalText = "Hello, World!"

        // 在Android中使用Base64进行编码
        val encodedString = android.util.Base64.encodeToString(originalText.toByteArray(), android.util.Base64.NO_WRAP)
        println("Encoded in Android: $encodedString")

        // 在Kotlin中使用Base64进行解码
        val decodedBytes = Base64.getDecoder().decode(encodedString)
        val decodedString = String(decodedBytes)
        println("Decoded in Kotlin: $decodedString")
    }
}