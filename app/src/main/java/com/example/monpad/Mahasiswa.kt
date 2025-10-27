package com.example.monpad

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.monpad.databinding.ActivityMahasiswaBinding

class Mahasiswa : AppCompatActivity() {

    private lateinit var binding: ActivityMahasiswaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}