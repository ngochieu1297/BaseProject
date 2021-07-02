package com.example.baseproject.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.model.Item
import com.example.baseproject.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: AppRepository) : ViewModel() {
    val usersLiveData = MutableLiveData<List<Item>>()
    val keyword = MutableLiveData<String>()

    fun searchUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val listUser = repository.searchUser(keyword.value ?: "")
            withContext(Dispatchers.Main) {
                usersLiveData.value = listUser.items ?: listOf()
            }
        }
    }
}
