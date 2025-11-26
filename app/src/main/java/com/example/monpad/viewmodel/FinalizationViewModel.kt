package com.example.monpad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.monpad.data.TokenManager
import com.example.monpad.network.FinalizationData
import com.example.monpad.network.RetrofitClient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FinalizationViewModel(application: Application) : AndroidViewModel(application) {

    private val _finalizationData = MutableLiveData<List<FinalizationData>>()
    val finalizationData: LiveData<List<FinalizationData>> = _finalizationData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val tokenManager = TokenManager(application)

    fun getFinalizationData() {
        viewModelScope.launch {
            val token = tokenManager.token.first()
            if (token.isNullOrEmpty()) {
                _error.value = "No auth token found. Please login again."
                return@launch
            }

            try {
                val response = RetrofitClient.apiService.getFinalizationData("Bearer $token")
                if (response.isSuccessful) {
                    _finalizationData.value = response.body()?.data
                } else {
                    _error.value = "Error fetching finalization data: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Failed to connect to the server: ${e.message}"
            }
        }
    }
}
