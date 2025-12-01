package com.example.monpad

import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.reflect.KClass
import com.example.monpad.Asisten
import com.example.monpad.Dosen
import com.example.monpad.Mahasiswa
import com.example.monpad.ProjectActivity

// Definisikan rute dan kelas Activity tujuan
sealed class Screen(val title: String, val icon: ImageVector, val route: KClass<*>) {

    // Data Screens
    data object Beranda : Screen("Beranda", Icons.Default.Home, DashboardDosen::class)
    data object DataDosen : Screen("Data Dosen", Icons.Default.Person, Dosen::class)
    data object DataAsisten : Screen("Data Asisten", Icons.Default.People, Asisten::class)
    data object DataMahasiswa : Screen("Data Mahasiswa", Icons.Default.AccountCircle, Mahasiswa::class)
    data object ProyekKelompok : Screen("Proyek & Kelompok", Icons.Default.Folder, ProjectActivity::class)

    // Log Out (Menggunakan kelas yang ada sebagai placeholder rute jika diperlukan, atau Any::class jika Anda mengabaikannya di AppDrawer)
    data object Logout : Screen("Log Out", Icons.Default.ExitToApp, Any::class)
}

data class MenuSection(
    val title: String,
    val items: List<Screen>,
    val isCollapsible: Boolean = false,
    val initialExpanded: Boolean = true
)

val menuStructure = listOf(
    MenuSection("Menu",
        listOf(
            Screen.Beranda,
            Screen.DataDosen,
            Screen.DataAsisten,
            Screen.DataMahasiswa,
            Screen.ProyekKelompok
        ),
        isCollapsible = true
    ),
    MenuSection("Akun",
        listOf(
            Screen.Logout // <-- Menggunakan object Logout yang didefinisikan di atas
        ),
        isCollapsible = true
    )
)

// Catatan: Pastikan kelas Activity ini ada di root package com.example.monpad
//class Dosen : ComponentActivity()
//class Asisten : ComponentActivity()
//class Mahasiswa : ComponentActivity()
//class ProjectActivity : ComponentActivity()
// class DashboardDosen sudah ada di file jpcompose