package com.example.baseproject.di

import com.example.baseproject.ui.detail.DetailViewModel
import com.example.baseproject.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel() }
}
