package com.example.monpad.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monpad.data.TokenManager
import com.example.monpad.network.LoginRequest
import com.example.monpad.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val message: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(context: Context) : ViewModel() {
    private val tokenManager = TokenManager(context)

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loginState.value = LoginState.Loading

                val response = RetrofitClient.apiService.login(
                    LoginRequest(email, password)
                )

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        // Simpan token
                        tokenManager.saveToken(loginResponse.data.token)

                        // Simpan user info berdasarkan role
                        tokenManager.saveUserInfo(
                            user = loginResponse.data.user,
                            role = loginResponse.data.role
                        )

                        _loginState.value = LoginState.Success("Login berhasil")
                    } else {
                        _loginState.value = LoginState.Error("Response kosong dari server")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    _loginState.value = LoginState.Error(
                        errorBody ?: "Error: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(
                    "Error: ${e.message ?: "Tidak dapat terhubung ke server"}"
                )
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}