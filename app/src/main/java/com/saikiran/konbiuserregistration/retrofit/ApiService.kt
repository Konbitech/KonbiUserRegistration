package com.saikiran.konbiuserregistration.retrofit

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/wp-json/wp/v2/kca/register-card-web-user")
    suspend fun createUser(@Body body: CreateUserRequest): Response<JsonObject>

    @POST("/?oauth=token")
    suspend fun getAccessToken(@Body body: AccessTokenRequest): Response<JsonObject>
}