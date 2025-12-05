package com.example.monpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.monpad.jpcompose.DashboardMahasiswaContent
import com.example.monpad.jpcompose.ui.theme.MonpadTheme


class DashboardMahasiswa : ComponentActivity() {

    // Menghapus penggunaan ViewModel yang tidak diperlukan untuk tampilan statis Dashboard Dosen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Memastikan tampilan Dashboard Dosen menggunakan tema aplikasi Anda
            MonpadTheme {
                DashboardMahasiswaContent()
            }
        }
    }
}