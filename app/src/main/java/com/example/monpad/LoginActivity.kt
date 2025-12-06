package com.example.monpad

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.monpad.data.TokenManager
import com.example.monpad.jpcompose.LoginScreen
import com.example.monpad.jpcompose.ui.theme.MonpadTheme
import com.example.monpad.viewmodel.LoginState
import com.example.monpad.viewmodel.LoginViewModel
import com.example.monpad.viewmodel.LoginViewModelFactory
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    private lateinit var tokenManager: TokenManager
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        tokenManager = TokenManager(this)

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(this)
        )[LoginViewModel::class.java]

        // ⭐ Observe login state untuk navigate setelah login berhasil
        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                when (state) {
                    is LoginState.Success -> {
                        navigateBasedOnRole()
                    }
                    else -> { /* Handle other states */ }
                }
            }
        }

        setContent {
            MonpadTheme {
                LoginScreen(
                    viewModel = viewModel,
                    onLoginSuccess = {
                        // Navigation handled by observer above
                    }
                )
            }
        }
    }

    private fun navigateBasedOnRole() {
        lifecycleScope.launch {
            // ⭐ Gunakan suspend function untuk get role
            val role = tokenManager.getUserRole()
            val name = tokenManager.getUserName()

            Toast.makeText(this@LoginActivity, "Login $name Berhasil!", Toast.LENGTH_SHORT).show()
            val intent = when (role?.lowercase()) {
                "dosen" -> {
                    // Arahkan ke Dashboard Dosen
                    Intent(this@LoginActivity, com.example.monpad.jpcompose.DashboardDosen::class.java)
                }
                "mahasiswa" -> {
                    // Arahkan ke Dashboard Mahasiswa

                    Intent(this@LoginActivity, DashboardMahasiswa::class.java)
                }
                "asisten" -> {
                    // Arahkan ke Dashboard Admin
                    Intent(this@LoginActivity, DashboardAsisten::class.java)
                }
                else -> {
                    // Default ke MainActivity
                    Intent(this@LoginActivity, MainActivity::class.java)
                }
            }

            startActivity(intent)
            finish()
        }
    }
}