package com.example.monpad

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.monpad.databinding.ActivityDosenBinding

class Dosen : AppCompatActivity() {

    private lateinit var binding: ActivityDosenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDosenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}