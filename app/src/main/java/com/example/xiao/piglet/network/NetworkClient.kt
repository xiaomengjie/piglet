package com.example.xiao.piglet.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NetworkClient {

    companion object{

//        const val BASE_URL = "http://119.91.136.187:8080/ktorpigletservice/"
        const val BASE_URL = "http://192.168.1.103:8080/"

//        const val BAIDU_BASE_URL = "https://fanyi-api.baidu.com/api/trans/vip/"

//        const val YD_BASE_URL = "https://openapi.youdao.com/"

        inline fun <reified T> create(): T =
            Retrofit.Builder().baseUrl(BASE_URL)
                .client(
                    OkHttpClient.Builder().apply {
                        addInterceptor(HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
//                        if (context != null) addInterceptor(LoadingInterceptor(LoadingDialog(context)))
                    }.build()
                )
                .addConverterFactory(GsonConverterFactory.create()).build().create()

        suspend fun request(context: Context){

        }
    }
}