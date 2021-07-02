package com.example.baseproject.source.remote

import com.example.baseproject.api.ApiService
import com.example.baseproject.model.User

class AppRemoteDataSourceImp(private val service: ApiService) : AppRemoteDataSource {
    override suspend fun searchUser(keyword: String): User {
        return service.searchUsers(keyword)
    }
}
