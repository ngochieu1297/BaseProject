package com.example.baseproject.di

import com.example.baseproject.repository.AppRepository
import com.example.baseproject.repository.AppRepositoryImp
import com.example.baseproject.source.local.AppLocalDataSource
import com.example.baseproject.source.local.AppLocalDataSourceImp
import com.example.baseproject.source.remote.AppRemoteDataSource
import com.example.baseproject.source.remote.AppRemoteDataSourceImp
import org.koin.dsl.module

val dataModule = module {
    single<AppLocalDataSource> { AppLocalDataSourceImp() }
    single<AppRemoteDataSource> { AppRemoteDataSourceImp(get()) }
    single<AppRepository> { AppRepositoryImp(get(), get()) }
}
