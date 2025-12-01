package com.example.monpad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.monpad.data.TokenManager
import com.example.monpad.network.Dosen
import com.example.monpad.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DosenViewModel(application: Application) : AndroidViewModel(application) {

    private val _dosenState = MutableStateFlow<UiState<List<Dosen>>>(UiState.Idle)
    val dosenState: StateFlow<UiState<List<Dosen>>> = _dosenState.asStateFlow()

    private val tokenManager = TokenManager(application)

    fun getDosen() {
        viewModelScope.launch {
            _dosenState.value = UiState.Loading

            val token = tokenManager.token.first()
            if (token.isNullOrEmpty()) {
                _dosenState.value = UiState.Error("No auth token found. Please login again.")
                return@launch
            }

            try {
                val response = RetrofitClient.apiService.getDosen("Bearer $token")
                if (response.isSuccessful && response.body() != null) {
                    _dosenState.value = UiState.Success(response.body()!!.data)
                } else {
                    _dosenState.value = UiState.Error(
                        "Error fetching dosen: ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                _dosenState.value = UiState.Error(
                    "Failed to connect to the server: ${e.message}"
                )
            }
        }
    }
}