package com.example.monpad

import com.example.monpad.jpcompose.NilaiMahasiswaScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*

class NilaiActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NilaiMahasiswaScreen()
        }
    }
}