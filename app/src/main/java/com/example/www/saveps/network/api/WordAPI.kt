package com.example.www.saveps.network.api

import com.example.www.saveps.bean.BaiduTranslateBean
import com.example.www.saveps.bean.Response
import com.example.www.saveps.bean.Word
import com.example.www.saveps.bean.YDResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface WordAPI {

    @FormUrlEncoded
    @POST("api")
    suspend fun translate(@Field("q") content: String): YDResponse

    @GET("word/queryAll")
    suspend fun queryAllWord(): Response<List<Word>>

    @POST("word/insert")
    suspend fun increaseWord(@Body word: Word): Response<Word>
}