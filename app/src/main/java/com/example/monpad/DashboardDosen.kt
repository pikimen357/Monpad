package com.example.monpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// Import DashboardDosenContent. Sesuaikan path import jika komponennya berbeda folder.
import com.example.monpad.jpcompose.DashboardDosenContent
import com.example.monpad.jpcompose.ui.theme.MonpadTheme // Import MonpadTheme Anda


class DashboardDosen : ComponentActivity() {

    // Menghapus penggunaan ViewModel yang tidak diperlukan untuk tampilan statis Dashboard Dosen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Memastikan tampilan Dashboard Dosen menggunakan tema aplikasi Anda
            MonpadTheme {
                DashboardDosenContent()
            }
        }
    }
}