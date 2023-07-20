package com.example.xiao.piglet.network

import com.example.xiao.piglet.tool.isChinese
import com.example.xiao.piglet.tool.sha256
import okhttp3.*
import java.util.UUID

class TranslateVerifyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.url.host == "openapi.youdao.com"){
            val newFormBodyBuilder = FormBody.Builder()
            val formBody = request.body as FormBody
            val content = formBody.value(0)
            newFormBodyBuilder.add("q", content)
            newFormBodyBuilder.add("from", if (content.isChinese()) "zh-CHS" else "en")
            newFormBodyBuilder.add("to", if (content.isChinese()) "en" else "zh-CHS")
            newFormBodyBuilder.add("appKey", YD_TRANSLATE_APP_KEY)
            val salt = UUID.randomUUID().toString()
            newFormBodyBuilder.add("salt", salt)
            val systemTime = (System.currentTimeMillis() / 1000).toString()
            newFormBodyBuilder.add("curtime", systemTime)
            newFormBodyBuilder.add("sign",
                "${YD_TRANSLATE_APP_KEY}${getInput(content)}$salt$systemTime$YD_TRANSLATE_SECRET".sha256()
            )
            newFormBodyBuilder.add("signType", "v3")
            val requestBody = newFormBodyBuilder.build()
            return chain.proceed(request.newBuilder().post(requestBody).build())
        }
        return chain.proceed(request)
    }

    private fun getInput(content: String): String {
        if (content.length <= 20) return content
        return content.substring(0, 10) + content.length + content.substring(content.length - 10, content.length)
    }

    companion object{
        private const val BAIDU_TRANSLATE_APP_ID = "20210519000833378"
        private const val BAIDU_TRANSLATE_SECRET = "XrhNGLMp6hHlt2L1i1Yf"

        private const val YD_TRANSLATE_APP_KEY = "1347009bafcb8933"
        private const val YD_TRANSLATE_SECRET = "V5T4XPbzd8YWwCJLxBiyoUX9Bwb5JZWo"
    }
}