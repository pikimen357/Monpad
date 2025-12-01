package com.example.monpad.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.monpad.data.TokenManager
import com.example.monpad.network.RetrofitClient
import com.example.monpad.network.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val _mahasiswaState = MutableStateFlow<UiState<List<Student>>>(UiState.Idle)
    val mahasiswaState: StateFlow<UiState<List<Student>>> = _mahasiswaState.asStateFlow()

    private val tokenManager = TokenManager(application)

    fun getMahasiswa() {
        viewModelScope.launch {
            _mahasiswaState.value = UiState.Loading

            val token = tokenManager.token.first()
            if (token.isNullOrEmpty()) {
                _mahasiswaState.value = UiState.Error("No auth token found. Please login again.")
                return@launch
            }

            try {
                val response = RetrofitClient.apiService.getMahasiswa("Bearer $token")
                if (response.isSuccessful && response.body() != null) {
                    _mahasiswaState.value = UiState.Success(response.body()!!.data)
                } else {
                    _mahasiswaState.value = UiState.Error(
                        "Error fetching students: ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                _mahasiswaState.value = UiState.Error(
                    "Failed to connect to the server: ${e.message}"
                )
            }
        }
    }
}