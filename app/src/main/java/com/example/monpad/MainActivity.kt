package com.example.monpad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.monpad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide action bar for splash screen effect
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnLogin.setOnClickListener {
                Log.d("MainActivity", "Login button clicked")
                try {
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    // Optional: Add transition animation
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    Log.d("MainActivity", "Intent to LoginActivity started successfully")
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error starting LoginActivity: ${e.message}", e)
                }
            }
        }
    }
}