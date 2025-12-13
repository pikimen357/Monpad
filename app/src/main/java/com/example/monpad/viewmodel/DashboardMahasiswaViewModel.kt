package com.example.monpad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.monpad.data.TokenManager
import com.example.monpad.network.DashboardGroup
import com.example.monpad.network.DashboardMahasiswa
import com.example.monpad.network.DashboardProject
import com.example.monpad.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import android.util.Log
import com.example.monpad.network.Grades

/**
 * ViewModel untuk mengambil data dashboard mahasiswa/asisten.
 */
class DashboardMahasiswaViewModel(application: Application) : AndroidViewModel(application) {

    private  val _dproject = MutableStateFlow<DashboardProject?>(null)
    val dproject: StateFlow<DashboardProject?> = _dproject

    // StateFlow untuk menyimpan status dan data DashboardAsisten
    private val _dashboardState = MutableStateFlow<UiState<DashboardMahasiswa>>(UiState.Idle)
    val dashboardState: StateFlow<UiState<DashboardMahasiswa>> = _dashboardState.asStateFlow()


    private val _dgroup = MutableStateFlow<DashboardGroup?>(null)
    val dgroup: StateFlow<DashboardGroup?> = _dgroup

    private val _grade = MutableStateFlow<Grades?>(null)
    val grade: StateFlow<Grades?> = _grade

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Instance untuk mengelola token
    private val tokenManager = TokenManager(application)


    /**
     * Mengambil data dashboard dari API.
     * Membutuhkan token otentikasi.
     */
    fun getDashboardData() {
        viewModelScope.launch {
            _dashboardState.value = UiState.Loading

            Log.d("DashboardVM", "=== START GET DASHBOARD DATA ===")

            // 1. Ambil Token
            val token = tokenManager.token.first()
            Log.d("DashboardVM", "Token: ${if (token.isNullOrEmpty()) "NULL/EMPTY" else "Available (${token.take(20)}...)"}")

            if (token.isNullOrEmpty()) {
                _dashboardState.value = UiState.Error("Token otentikasi tidak ditemukan. Silakan login kembali.")
                return@launch
            }

            // 2. Lakukan Panggilan API
            try {
                Log.d("DashboardVM", "Calling API: /api/dashboard/mahasiswa")
                val response = RetrofitClient.dashboardApiService.getDashboardMhsData("Bearer $token")

                Log.d("DashboardVM", "Response Code: ${response.code()}")
                Log.d("DashboardVM", "Response Success: ${response.isSuccessful}")
                Log.d("DashboardVM", "Response Body: ${response.body()}")
                Log.d("DashboardVM", "Response Error Body: ${response.errorBody()?.string()}")

                if (response.isSuccessful && response.body() != null) {
                    val dashboardData = response.body()!!
                    _dashboardState.value = UiState.Success(dashboardData)

                    // Log data yang didapat
                    Log.d("DashboardVM", "Dashboard Data: $dashboardData")
                    Log.d("DashboardVM", "Grades: ${dashboardData.data?.grades}")
                    Log.d("DashboardVM", "Groups: ${dashboardData.data?.groups}")
                    Log.d("DashboardVM", "Project: ${dashboardData.data?.grades?.group?.project}")

                    // ✅ PERBAIKAN: Ambil dari groups[0] bukan dari grades.group
                    val firstGroup = dashboardData.data?.groups?.firstOrNull()
                    _dgroup.value = firstGroup
                    _dproject.value = firstGroup?.project
                    _grade.value = dashboardData.data?.grades

                    Log.d("DashboardVM", "Project Name: ${_dproject.value?.nama_projek}")
                    Log.d("DashboardVM", "Project Description: ${_dproject.value?.deskripsi}")
                    Log.d("DashboardVM", "Project Owner: ${_dproject.value?.owner?.username}")

                } else {
                    val errorMsg = "Gagal mengambil data dashboard: ${response.message()} (Code: ${response.code()})"
                    Log.e("DashboardVM", errorMsg)
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