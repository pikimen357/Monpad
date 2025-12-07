package com.example.monpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.monpad.jpcompose.DashboardMahasiswaContent
import com.example.monpad.jpcompose.ui.theme.MonpadTheme
import com.example.monpad.viewmodel.DashboardMahasiswaViewModel


class DashboardMahasiswa: ComponentActivity() {

    private val dashboardViewModel: DashboardMahasiswaViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Memastikan tampilan Dashboard Mahasiswa menggunakan tema aplikasi Anda
            MonpadTheme {
                DashboardMahasiswaContent(dashboardViewModel)
            }
            dashboardViewModel.getDashboardData()

        }
    }
}