package com.example.monpad.jpcompose

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monpad.viewmodel.LoginState
import com.example.monpad.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    val viewModel = remember { LoginViewModel(context) }
    val loginState by viewModel.loginState.collectAsState()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Handle login state
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                Toast.makeText(
                    context,
                    (loginState as LoginState.Success).message,
                    Toast.LENGTH_SHORT
                ).show()
                onLoginSuccess()
            }
            is LoginState.Error -> {
                errorMessage = (loginState as LoginState.Error).message
                showError = true
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF6A4C93),
                            Color(0xFF8B6FB5)
                        )
                    )
                )
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .width(370.dp)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Log In",
                            fontSize = 45.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        // Email/Username
                        Text(
                            text = "Email",
                            fontSize = 16.sp,
                            color = Color(0xFF1A141F),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        )

                        OutlinedTextField(
                            value = username,
                            onValueChange = {
                                username = it
                                showError = false
                            },
                            placeholder = {
                                Text(
                                    "Masukkan email",
                                    fontSize = 13.sp,
                                    color = Color(0xFFA7A7A7)
                                )
                            },
                            enabled = loginState !is LoginState.Loading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF52357B),
                                unfocusedBorderColor = Color(0xFFE0E0E0),
                                focusedTextColor = Color(0xFF1A141F),
                                unfocusedTextColor = Color(0xFF1A141F)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true
                        )

                        // Password
                        Text(
                            text = "Password",
                            fontSize = 16.sp,
                            color = Color(0xFF1A141F),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp)
                        )

                        OutlinedTextField(
                            value = password,
                            onValueChange = {
                                password = it
                                showError = false
                            },
                            placeholder = {
                                Text(
                                    "Masukkan password",
                                    fontSize = 13.sp,
                                    color = Color(0xFFA7A7A7)
                                )
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            enabled = loginState !is LoginState.Loading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF52357B),
                                unfocusedBorderColor = Color(0xFFE0E0E0),
                                focusedTextColor = Color(0xFF1A141F),
                                unfocusedTextColor = Color(0xFF1A141F)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true
                        )

                        // Remember Me & Forgot Password
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Checkbox(
                                    checked = rememberMe,
                                    onCheckedChange = { rememberMe = it },
                                    enabled = loginState !is LoginState.Loading,
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color(0xFF52357B)
                                    )
                                )
                                Text(
                                    text = "Ingat saya",
                                    fontSize = 14.sp,
                                    color = Color(0xFF828282)
                                )
                            }

                            TextButton(
                                onClick = { /* Handle forgot password */ },
                                enabled = loginState !is LoginState.Loading
                            ) {
                                Text(
                                    text = "Lupa Sandi?",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF828282)
                                )
                            }
                        }

                        // Tombol Masuk
                        Button(
                            onClick = {
                                if (username.isNotBlank() && password.isNotBlank()) {
                                    viewModel.login(username, password)
                                } else {
                                    errorMessage = "Email dan password harus diisi"
                                    showError = true
                                }
                            },
                            enabled = loginState !is LoginState.Loading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp)
                                .height(45.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF52357B)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            if (loginState is LoginState.Loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    text = "Masuk",
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                // Error Message Card
                AnimatedVisibility(
                    visible = showError,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically(),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFEBEE)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Error",
                                tint = Color(0xFFC62828),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = errorMessage,
                                fontSize = 14.sp,
                                color = Color(0xFFC62828),
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { showError = false },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color(0xFFC62828),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
