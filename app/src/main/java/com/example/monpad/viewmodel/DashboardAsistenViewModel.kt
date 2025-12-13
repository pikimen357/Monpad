package com.example.monpad.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.monpad.data.TokenManager
import com.example.monpad.network.DataDetail // Import DataDetail dari data model baru
import com.example.monpad.network.DataDetailAsisten
import com.example.monpad.network.ResponseData // Import ResponseData dari data model baru
import com.example.monpad.network.ResponseDataAsisten
import com.example.monpad.network.RetrofitClient // Pastikan RetrofitClient mengarah ke ApiService yang benar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DashboardAsistenViewModel(application: Application) : AndroidViewModel(application) {

    private val _dshAstDetail  = MutableStateFlow<DataDetailAsisten?>(null)
    val dshAstDetail: StateFlow<DataDetailAsisten?> = _dshAstDetail.asStateFlow()

    // Mengganti DashboardMahasiswa dengan ResponseData (sesuai struktur respons baru)
    private val _dashboardState = MutableStateFlow<UiState<ResponseDataAsisten>>(UiState.Idle)
    val dashboardState: StateFlow<UiState<ResponseDataAsisten>> = _dashboardState.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Instance untuk mengelola token
    private val tokenManager = TokenManager(application)


    fun getDashboardAsistenData(){
        viewModelScope.launch {
            _dashboardState.value = UiState.Loading
            // 1. Ambil Token
            val token = tokenManager.token.first()
            Log.d("DashboardVM", "Token: ${if (token.isNullOrEmpty()) "NULL/EMPTY" else "Available (${token.take(20)}...)"}")

            if (token.isNullOrEmpty()) {
                _dashboardState.value = UiState.Error("Token otentikasi tidak ditemukan. Silakan login kembali.")
                return@launch
            }

            // 2. Lakukan Panggilan API
            try {
                Log.d("DashboardVM", "Calling API: /api/dashboard/asisten")

                // Pastikan RetrofitClient.dashboardApiService mengimplementasikan DashboardApiService yang baru
                val response = RetrofitClient.dashboardApiService.getDashboardAstData("Bearer $token")

                Log.d("DashboardVM", "Response Code: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
                    val dashboardData = response.body()!!

                    // ✅ PENYESUAIAN: Simpan ResponseData ke _dashboardState
                    _dashboardState.value = UiState.Success(dashboardData)

                    // ✅ PENYESUAIAN: Simpan DataDetail ke _dshdetail
                    _dshAstDetail.value = dashboardData.data

                    // Log data yang didapat
                    Log.d("DashboardVM", "Dashboard Data: $dashboardData")
                    Log.d("DashboardVM", "Jumlah Mahasiswa: ${dashboardData.data.jumlah_mahasiswa}")
                    Log.d("DashboardVM", "Rata-rata: ${dashboardData.data.rata_rata}")


                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMsg = "Gagal mengambil data dashboard: ${response.message()} (Code: ${response.code()})"
                    Log.e("DashboardVM", errorMsg)
                    Log.e("DashboardVM", "Error Body: $errorBody")
                    _dashboardState.value = UiState.Error(errorMsg)
                }
            } catch (e: Exception) {
                val errorMsg = "Koneksi gagal: ${e.message}"
                Log.e("DashboardVM", errorMsg, e)
                e.printStackTrace()
                _dashboardState.value = UiState.Error(errorMsg)
            }
            Log.d("DashboardVM", "=== END GET DASHBOARD DATA ===")
        }
    }

}