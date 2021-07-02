package com.example.baseproject.repository

import com.example.baseproject.model.User

interface AppRepository {
    suspend fun searchUser(keyword: String): User
}
