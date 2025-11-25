package com.example.monpad

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.monpad.jpcompose.LoginScreen
import com.example.monpad.jpcompose.ui.theme.MonpadTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MonpadTheme {
                LoginScreen(
                    onLoginSuccess = {
                        // Navigate ke MainActivity atau Dashboard
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}
