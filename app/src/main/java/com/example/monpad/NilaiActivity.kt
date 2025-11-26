package com.example.monpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.monpad.jpcompose.NilaiMahasiswaScreen
import com.example.monpad.viewmodel.FinalizationViewModel

class NilaiActivity : ComponentActivity() {
    private val finalizationViewModel: FinalizationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NilaiMahasiswaScreen(finalizationViewModel)
        }
        finalizationViewModel.getFinalizationData()
    }
}