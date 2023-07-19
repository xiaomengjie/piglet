package com.example.www.saveps.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NetworkClient {

    companion object{
        const val BASE_URL = "http://119.91.136.187:8080/ktorsavepsservice/"
//        const val BASE_URL = "http://192.168.1.100:8090/"

        const val BAIDU_BASE_URL = "https://fanyi-api.baidu.com/api/trans/vip/"

        const val YD_BASE_URL = "https://openapi.youdao.com/"

        inline fun <reified T> create(baseUrl: String = BASE_URL): T =
            Retrofit.Builder().baseUrl(baseUrl)
                .client(
                    OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }).addInterceptor(TranslateVerifyInterceptor()).build()
                )
                .addConverterFactory(GsonConverterFactory.create()).build().create()
    }
}