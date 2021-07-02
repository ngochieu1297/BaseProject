package com.example.baseproject.repository

import com.example.baseproject.model.User
import com.example.baseproject.source.local.AppLocalDataSource
import com.example.baseproject.source.remote.AppRemoteDataSource

class AppRepositoryImp(
    private val local: AppLocalDataSource,
    private val remote: AppRemoteDataSource
) : AppRepository {
    override suspend fun searchUser(keyword: String): User {
        return remote.searchUser(keyword)
    }
}
