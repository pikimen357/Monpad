package com.example.monpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.monpad.jpcompose.DashboardAssitenContent
import com.example.monpad.jpcompose.ui.theme.MonpadTheme
import com.example.monpad.viewmodel.DashboardAsistenViewModel
import kotlin.getValue


class DashboardAsisten : ComponentActivity() {

    private val viewModel: DashboardAsistenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Memastikan tampilan Dashboard Dosen menggunakan tema aplikasi Anda
            MonpadTheme {
                DashboardAssitenContent(viewModel)
            }
        }
    }
}