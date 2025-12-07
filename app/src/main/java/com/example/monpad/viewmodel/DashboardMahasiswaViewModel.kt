package com.example.monpad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.monpad.data.TokenManager // Asumsi TokenManager berada di paket ini
import com.example.monpad.network.DashboardAsisten
import com.example.monpad.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel untuk mengambil data dashboard mahasiswa/asisten.
 */
class DashboardMahasiswaViewModel(application: Application) : AndroidViewModel(application) {

    // StateFlow untuk menyimpan status dan data DashboardAsisten
    private val _dashboardState = MutableStateFlow<UiState<DashboardAsisten>>(UiState.Idle)
    val dashboardState: StateFlow<UiState<DashboardAsisten>> = _dashboardState.asStateFlow()

    // Instance untuk mengelola token
    private val tokenManager = TokenManager(application)

    /**
     * Mengambil data dashboard dari API.
     * Membutuhkan token otentikasi.
     */
    fun getDashboardData() {
        viewModelScope.launch {
            _dashboardState.value = UiState.Loading

            // 1. Ambil Token
            val token = tokenManager.token.first()
            if (token.isNullOrEmpty()) {
                _dashboardState.value = UiState.Error("Token otentikasi tidak ditemukan. Silakan login kembali.")
                return@launch
            }

            // 2. Lakukan Panggilan API
            try {
                // Panggil endpoint menggunakan dashboardApiService
                val response = RetrofitClient.dashboardApiService.getDashboardMhsData("Bearer $token")

                if (response.isSuccessful && response.body() != null) {
                    // Sukses: Update State dengan data DashboardAsisten
                    _dashboardState.value = UiState.Success(response.body()!!)
                } else {
                    // Gagal: Tampilkan pesan error dari response
                    _dashboardState.value = UiState.Error(
                        "Gagal mengambil data dashboard: ${response.message()} (Code: ${response.code()})"
                    )
                }
            } catch (e: Exception) {
                // Exception: Gagal koneksi atau parsing
                _dashboardState.value = UiState.Error(
                    "Koneksi gagal: ${e.message ?: "Terjadi kesalahan yang tidak diketahui."}"
                )
            }
        }
    }
}