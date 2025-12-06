package com.example.monpad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.monpad.data.TokenManager
import com.example.monpad.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isNavigating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set layout terlebih dahulu
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide action bar for splash screen effect
        supportActionBar?.hide()

        val tokenManager = TokenManager(this)

        // Setup button click listener
        binding.btnLogin.setOnClickListener {
            if (!isNavigating) {
                Log.d("MainActivity", "Login button clicked")
                isNavigating = true

                // Tampilkan loading jika ada
                // binding.progressBar.visibility = View.VISIBLE

                lifecycleScope.launch {
                    try {
                        // Check if there is a token saved
                        val token = tokenManager.getToken()
                        val userRole = tokenManager.getUserRole()

                        if (token != null) {
                            // Token ada, user sudah pernah login
                            // Langsung arahkan ke dashboard sesuai role
                            Log.d("MainActivity", "Token found, role: $userRole")

                            val intent = when (userRole) {
                                "mahasiswa" -> {
                                    Intent(this@MainActivity, DashboardMahasiswa::class.java)
                                }
                                "asisten" -> {
                                    Intent(this@MainActivity, DashboardAsisten::class.java)
                                }
                                "dosen" -> {
                                    Intent(this@MainActivity, DashboardDosen::class.java)
                                }
                                else -> {
                                    // Role tidak dikenali, arahkan ke login
                                    Log.w("MainActivity", "Unknown role: $userRole")
                                    Intent(this@MainActivity, LoginActivity::class.java)
                                }
                            }

                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                            finish()

                        } else {
                            // Token tidak ada, user belum login
                            // Arahkan ke halaman login
                            Log.d("MainActivity", "No token found, navigating to login")

                            val intent = Intent(this@MainActivity, LoginActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        }

                    } catch (e: Exception) {
                        Log.e("MainActivity", "Error checking token: ${e.message}", e)
                        // Jika error, tetap arahkan ke login
                        val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    } finally {
                        isNavigating = false
                        // binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }
}