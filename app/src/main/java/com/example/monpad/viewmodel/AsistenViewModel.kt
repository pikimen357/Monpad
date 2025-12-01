package com.example.monpad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.monpad.data.TokenManager
import com.example.monpad.network.Assistant
import com.example.monpad.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AssistantViewModel(application: Application) : AndroidViewModel(application) {

    private val _assistantState = MutableStateFlow<UiState<List<Assistant>>>(UiState.Idle)
    val assistantState: StateFlow<UiState<List<Assistant>>> = _assistantState.asStateFlow()

    private val tokenManager = TokenManager(application)

    fun getAssistants() {
        viewModelScope.launch {
            _assistantState.value = UiState.Loading

            val token = tokenManager.token.first()
            if (token.isNullOrEmpty()) {
                _assistantState.value = UiState.Error("No auth token found. Please login again.")
                return@launch
            }

            try {
                val response = RetrofitClient.apiService.getAsisten("Bearer $token")
                if (response.isSuccessful && response.body() != null) {
                    _assistantState.value = UiState.Success(response.body()!!.data)
                } else {
                    _assistantState.value = UiState.Error(
                        "Error fetching assistants: ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                _assistantState.value = UiState.Error(
                    "Failed to connect to the server: ${e.message}"
                )
            }
        }
    }
}