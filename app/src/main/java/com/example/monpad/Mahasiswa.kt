package com.example.monpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.monpad.jpcompose.DataMahasiswaScreen
import com.example.monpad.viewmodel.StudentViewModel

class Mahasiswa : ComponentActivity() {

    private val studentViewModel: StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DataMahasiswaScreen(studentViewModel)
        }

        // Fetch data mahasiswa
        studentViewModel.getMahasiswa()
    }
}