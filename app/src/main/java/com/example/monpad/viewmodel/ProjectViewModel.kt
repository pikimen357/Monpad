package com.example.monpad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.monpad.data.TokenManager
import com.example.monpad.network.Project
import com.example.monpad.network.RetrofitClient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProjectViewModel(application: Application) : AndroidViewModel(application) {

    private val _projects = MutableLiveData<List<Project>>()
    val projects: LiveData<List<Project>> = _projects

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val tokenManager = TokenManager(application)

    fun getProjects() {
        viewModelScope.launch {
            val token = tokenManager.token.first()
            if (token.isNullOrEmpty()) {
                _error.value = "No auth token found. Please login again."
                return@launch
            }

            try {
                val response = RetrofitClient.apiService.getProjects("Bearer $token")
                if (response.isSuccessful) {
                    _projects.value = response.body()?.data
                } else {
                    _error.value = "Error fetching projects: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Failed to connect to the server: ${e.message}"
            }
        }
    }
}
