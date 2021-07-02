package com.example.baseproject.source.remote

import com.example.baseproject.model.User

interface AppRemoteDataSource {
    suspend fun searchUser(keyword: String): User
}
