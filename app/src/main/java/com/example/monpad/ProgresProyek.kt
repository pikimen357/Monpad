package com.example.monpad

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.monpad.jpcompose.DashboardMahasiswaContent
import com.example.monpad.jpcompose.ProgresProyekContent
import com.example.monpad.jpcompose.ui.theme.MonpadTheme
import com.example.monpad.viewmodel.DashboardMahasiswaViewModel
import kotlin.getValue

class ProgresProyek : AppCompatActivity() {
    private val dashboardViewModel: DashboardMahasiswaViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Memastikan tampilan Dashboard Mahasiswa menggunakan tema aplikasi Anda
            MonpadTheme {
                ProgresProyekContent(dashboardViewModel)
            }
            dashboardViewModel.getDashboardData()

        }
    }
}