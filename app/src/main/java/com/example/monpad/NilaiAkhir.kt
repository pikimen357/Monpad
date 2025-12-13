package com.example.monpad

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.monpad.jpcompose.NilaiAkhirContent
import com.example.monpad.jpcompose.ui.theme.MonpadTheme
import com.example.monpad.viewmodel.DashboardMahasiswaViewModel
import kotlin.getValue

class NilaiAkhir : AppCompatActivity() {

    private val dashboardViewModel: DashboardMahasiswaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MonpadTheme {
                NilaiAkhirContent(dashboardViewModel)
            }
            dashboardViewModel.getDashboardData()
        }
    }
}