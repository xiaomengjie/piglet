package com.example.xiao.piglet.network.api

import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface HandshakeAPI {

    @POST("ssl/client_hello")
    suspend fun hello(): ResponseBody

    @POST("ssl/aes_key")
    suspend fun key(@Body byteArray: RequestBody): ResponseBody
}