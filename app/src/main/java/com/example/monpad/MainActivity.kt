package com.example.monpad

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

            btnDosen.setOnClickListener {
                startActivity(Intent(this@MainActivity,
                    Dosen::class.java))
            }

            btnProject.setOnClickListener {
                startActivity(Intent(this@MainActivity,
                    ProjectActivity::class.java))
            }

            btnNilai.setOnClickListener {
                startActivity(Intent(this@MainActivity,
                    NilaiActivity::class.java))
            }

            // Di MainActivity
            btnDashboardDosen.setOnClickListener {
                startActivity(Intent(this@MainActivity,
                    com.example.monpad.jpcompose.DashboardDosen::class.java))
            }

            btnLogin.setOnClickListener {
                Log.d("MainActivity", "Button clicked")
                try {
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    Log.d("MainActivity", "Intent started")
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error: ${e.message}")
                }
            }

            btnDashboardMhs.setOnClickListener {
                startActivity(Intent(this@MainActivity,
                    DashboardMahasiswa::class.java))
            }
        }

    }
}