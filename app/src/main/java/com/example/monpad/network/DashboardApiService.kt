package com.example.monpad.network

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

// =======================================================
// Data Classes KHUSUS UNTUK ENDPOINT /api/dashboard/mahasiswa
// =======================================================

// Kelas utama untuk response API Dashboard
data class DashboardMahasiswa(
    @SerializedName("data")
    val data: DashboardData? = null
)

// Kelas untuk objek "data"
data class DashboardData(
    @SerializedName("grades")
    val grades: Grades? = null,
    @SerializedName("groups")
    val groups: List<DashboardGroup>? = null // Menggunakan DashboardGroup untuk spesifik dashboard
)

// Kelas untuk objek "grades"
data class Grades(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("user")
    val user: DashboardUser? = null, // Menggunakan DashboardUser
    @SerializedName("group")
    val group: DashboardGroup? = null, // Menggunakan DashboardGroup
    @SerializedName("member_grade")
    val memberGrade: Map<String, Any>? = null,
    @SerializedName("project_grade")
    val projectGrade: Double? = null,
    @SerializedName("personal_grade")
    val personalGrade: String? = null,
    @SerializedName("final_grade")
    val finalGrade: String? = null,
    @SerializedName("confirmed")
    val confirmed: Int? = null
)

// Kelas untuk objek di dalam array "groups" dan di dalam "grades.group"
data class DashboardGroup(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("nama")
    val nama: String? = null,
    @SerializedName("anggota")
    val anggota: List<Student>? = null, // Re-use Student (Mahasiswa)
    @SerializedName("project")
    val project: DashboardProject? = null, // Menggunakan DashboardProject
    @SerializedName("project_id")
    val projectId: Int? = null
)

// Kelas untuk User di dalam Grades (Mirip Student/Anggota)
data class DashboardUser(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("nim")
    val nim: String? = null,
    @SerializedName("angkatan")
    val angkatan: String? = null,
    @SerializedName("prodi")
    val prodi: String? = null,
    @SerializedName("jabatan")
    val jabatan: String? = null
)

// Kelas untuk objek "project" di Dashboard
data class DashboardProject(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("nama_projek")
    val nama_projek: String? = null,
    @SerializedName("semester")
    val semester: Int? = null,
    @SerializedName("deskripsi")
    val deskripsi: String? = null,
    @SerializedName("tahun_ajaran")
    val tahun_ajaran: Int? = null,
    @SerializedName("owner")
    val owner: DashboardOwner? = null, // Menggunakan DashboardOwner
    @SerializedName("asisten")
    val asisten: DashboardAssistant? = null, // Menggunakan DashboardAssistant
    @SerializedName("finalized")
    val finalized: Boolean? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("week_period")
    val weekPeriod: String? = null,
    @SerializedName("grade")
    val grade: Double? = null
)

// Kelas untuk objek "owner" di Dashboard Project (Mirip Dosen)
data class DashboardOwner(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("nidn")
    val nidn: String? = null,
    @SerializedName("fakultas")
    val fakultas: String? = null
)

// Kelas untuk objek "asisten" di Dashboard Project
data class DashboardAssistant(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("tahun_ajaran")
    val tahunAjaran: String? = null,
    @SerializedName("nim")
    val nim: String? = null
)

interface DashboardApiService {

    @GET("api/dashboard/mahasiswa")
    suspend fun getDashboardMhsData(@Header("Authorization") token: String): Response<DashboardMahasiswa>
}