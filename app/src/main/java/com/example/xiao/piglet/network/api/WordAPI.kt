package com.example.xiao.piglet.network.api

import com.example.xiao.piglet.bean.Response
import com.example.xiao.piglet.bean.Word
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WordAPI {

//    @FormUrlEncoded
//    @POST("api")
//    suspend fun translate(@Field("q") content: String): YDResponse

    @POST("word/insert")
    suspend fun increaseWord(@Body words: List<Word>): Response<Boolean>

    @POST("word/delete")
    suspend fun deleteWord(@Body englishes: List<String>): Response<Boolean>

    @GET("word/query")
    suspend fun queryWord(
        @Query("english") english: String = "",
        @Query("chinese") chinese: String = ""): Response<List<Word>>
}