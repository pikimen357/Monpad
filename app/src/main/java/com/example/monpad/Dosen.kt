package com.example.monpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.monpad.jpcompose.DataDosenScreen
import com.example.monpad.viewmodel.DosenViewModel

class Dosen : ComponentActivity() {

    private val dosenViewModel: DosenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DataDosenScreen(dosenViewModel)
        }

        // Fetch data dosen
        dosenViewModel.getDosen()
    }
}