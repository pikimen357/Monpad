package com.example.monpad

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.monpad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            btnMahasiswa.setOnClickListener {
                startActivity(Intent(this@MainActivity,
                    Mahasiswa::class.java))
            }

            btnAsisten.setOnClickListener {
                startActivity(Intent(this@MainActivity,
                    Asisten::class.java))
            }

            btnProject.setOnClickListener {
                startActivity(Intent(this@MainActivity,
                    ProjectActivity::class.java))
            }

            btnNilai.setOnClickListener {
                startActivity(Intent(this@MainActivity,
                    NilaiActivity::class.java))
            }
        }

    }
}