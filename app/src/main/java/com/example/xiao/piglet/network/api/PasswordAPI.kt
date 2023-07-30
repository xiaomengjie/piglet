package com.example.xiao.piglet.network.api

import com.example.xiao.piglet.bean.Password
import com.example.xiao.piglet.bean.Response
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PasswordAPI {

    //增加密码
    @POST("password/insert")
    suspend fun insertPassword(@Body password: Password): Response<Boolean>

    //通过位置删除密码
    @POST("password/delete")
    suspend fun deletePassword(@Body requestBody: RequestBody): Response<Boolean>

    //删除所有密码
    @POST("password/deleteAll")
    suspend fun deleteAllPassword(): Response<Boolean>

    //更新密码
    @POST("password/update")
    suspend fun updatePassword(@Body password: Password): Response<Boolean>

    //通过位置查询密码
    @GET("password/query")
    suspend fun searchPassword(@Query("name") name: String): Response<Password>

    //查询所有密码
    @GET("password/queryAll")
    suspend fun queryAllPassword(): Response<List<Password>>

}