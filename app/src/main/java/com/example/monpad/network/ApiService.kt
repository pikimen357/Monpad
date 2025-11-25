package com.example.monpad.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val data: LoginData
)

data class LoginData(
    val user: User,
    val role: String,
    val token: String
)

// Universal User model yang bisa menampung semua role
data class User(
    val id: Int,
    val username: String,
    val email: String,
)

interface ApiService {
    @POST("api/login") // Sesuaikan dengan endpoint Anda
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}