package com.example.monpad.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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

data class ProjectResponse(
    val data: List<Project>
)

data class Project(
    val id: Int,
    val nama_projek: String,
    val semester: Int,
    val deskripsi: String,
    val tahun_ajaran: Int,
    val owner: Owner,
    val asisten: Assistant,
    val finalized: Boolean,
    val updated_at: String,
    val week_period: String,
    val grade: Int
)

data class Owner(
    val id: Int,
    val username: String,
    val email: String,
    val nidn: String,
    val fakultas: String
)

data class AssistantResponse(
    val data: List<Assistant>
)

data class Assistant(
    val id: Int,
    val username: String,
    val email: String,
    val tahun_ajaran: String,
    val nim: String
)


data class FinalizationResponse(
    val data: List<FinalizationData>
)

data class FinalizationData(
    val user: Student,
    val group: Group,
    val project_grade: Double,
    val personal_grade: String,
    val final_grade: String,
    val confirmed: Int
)

data class MahasiswaResponse(
    val data: List<Student>
)

data class Student(
    val id: Int,
    val username: String,
    val email: String,
    val nim: String,
    val angkatan: String,
    val prodi: String,
    val jabatan: String
)

data class DosenResponse(
    val data: List<Dosen>
)

data class Dosen(
    val id: Int,
    val username: String,
    val email: String,
    val nidn: String,
    val fakultas: String
)

data class Group(
    val id: Int,
    val nama: String,
    val anggota: List<Student>,
    val project: Project
)

interface ApiService {
    @POST("api/login") // Sesuaikan dengan endpoint Anda
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("api/project")
    suspend fun getProjects(@Header("Authorization") token: String): Response<ProjectResponse>

    @GET("api/mahasiswa")
    suspend fun getMahasiswa(@Header("Authorization") token: String): Response<MahasiswaResponse>

    @GET("api/asisten")
    suspend fun getAsisten(@Header("Authorization") token: String): Response<AssistantResponse>


    @GET("api/dosen")
    suspend fun getDosen(@Header("Authorization") token: String): Response<DosenResponse>

    @GET("api/finalization/")
    suspend fun getFinalizationData(@Header("Authorization") token: String): Response<FinalizationResponse>
}
