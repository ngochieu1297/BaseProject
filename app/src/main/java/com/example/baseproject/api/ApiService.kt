package com.example.baseproject.api

import com.example.baseproject.model.User
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun searchUsers(@Query("q") keyword: String): User
}
