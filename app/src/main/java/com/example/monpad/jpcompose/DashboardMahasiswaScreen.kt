package com.example.monpad.jpcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import com.example.monpad.LoginActivity
import com.example.monpad.NilaiActivity
import com.example.monpad.ProjectActivity
import com.example.monpad.jpcompose.ui.theme.*
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import kotlin.reflect.KClass
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.monpad.jpcompose.ui.theme.Purple40

// Data class untuk menu item Mahasiswa
data class ScreenMhs(
    val title: String,
    val icon: ImageVector,
    val route: KClass<out ComponentActivity>
)

// Data class untuk menu section
data class MenuSectionMhs(
    val title: String,
    val items: List<ScreenMhs>,
    val isCollapsible: Boolean = false,
    val initialExpanded: Boolean = false
)

class DashboardMahasiswaScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonpadTheme {
                DashboardAssitenContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardMahasiswaContent() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentActivity = DashboardMahasiswaScreen::class
    val context = LocalContext.current


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppMahasiswaDrawer(
                closeDrawer = { scope.launch { drawerState.close() } },
                currentActivity = currentActivity
            )
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigationBarMhs(
                    // Panggil fungsi untuk membuka drawer saat tombol menu di bottom bar diklik
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .background(PurpleBackground)
        ) { innerPadding ->
            // Wrap the entire content in a Column to place the header above the scrollable area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding) // Apply padding from Scaffold
                    .background(PurpleBackground) // Set the overall background color
            ) {
                // Header is now outside the scrollable Column
                HeaderMahasiswaSection(
                    onMenuClick = { scope.launch { drawerState.open() } }
                )

                // This new Column will contain all the scrollable content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()) // Only this Column scrolls
                        .padding(bottom = 50.dp) // Keep padding for content visibility
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 50.dp)
                    ) {
                        NilaiCardSection()

                        Spacer(modifier = Modifier.height(50.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            )
                            {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp, 40.dp)
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 30.dp)
                                ) {
                                    Text(
                                        text = "Monitoring PAD",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Purple40,
                                        modifier = Modifier.padding(bottom = 15.dp)
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Text(
                                        text = "Catatan pengajar: ",
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "Proyek ini perlu perbaikan pada bagian dashboard assiten dan dosen yang mana datanya tidak benar benar dinamis",
                                        fontSize = 15.sp,
                                        textAlign = TextAlign.Justify,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Normal
                                    )

                                    Spacer(modifier = Modifier.height(18.dp))

                                    Column(modifier = Modifier.padding(start = 15.dp)) {
                                        Text(
                                            text = "Project Owner : Govan",
                                            fontSize = 11.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Spacer(modifier = Modifier.height(2.dp))

                                        Text(
                                            text = "Asisten : Govan",
                                            fontSize = 11.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Spacer(modifier = Modifier.height(2.dp))

                                        Text(
                                            text = "Jumlah Anggota : 4",
                                            fontSize = 11.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }


                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun NilaiCardSection() {
    // Data dummy untuk tampilan nilai
    val nilaiAkhir = "90"
    val nilaiProyek = "80"
    val nilaiUAS = "90"
    val nilaiUTS = "88"
    val nilaiPersonal = "79"

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        // Bagian Nilai Akhir
        Text(
            text = nilaiAkhir,
            fontSize = 72.sp, // Ukuran huruf besar untuk nilai utama
            fontWeight = FontWeight.Black,
            color = Color.Black
        )
        Text(
            text = "Nilai Akhir",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Grid 2x2 untuk Nilai Detail
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NilaiDetailCard(title = "Nilai Proyek", value = nilaiProyek, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(16.dp))
                NilaiDetailCard(title = "UAS", value = nilaiUAS, modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NilaiDetailCard(title = "UTS", value = nilaiUTS, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(16.dp))
                NilaiDetailCard(title = "Nilai Personal", value = nilaiPersonal, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun NilaiDetailCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(100.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Purple40 // Gunakan warna ungu untuk kontras
            )
        }
    }
}

@Composable
fun HeaderMahasiswaSection(onMenuClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(Color.White)
            .padding(16.dp)
            .height(70.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Dashboard Mahasiswa",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Beranda > Dashboard Mahasiswa",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            IconButton(onClick = { /* Handle settings click */ }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.Black
                )
            }
        }
    }
}



@Composable
fun BottomNavigationBarMhs(onMenuClick: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope() // Diperlukan untuk onClick

    NavigationBar(
        containerColor = Color.White, // Warna latar belakang Bottom Bar
        tonalElevation = 8.dp,
        contentColor = PurpleBackground, // Warna default untuk ikon dan label jika tidak dioverride
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = true,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PurpleBackground, // Warna ikon saat terpilih
                unselectedIconColor = Color.Gray, // Warna ikon saat tidak terpilih
                selectedTextColor = PurpleBackground, // Warna label saat terpilih
                unselectedTextColor = Color.Gray, // Warna label saat tidak terpilih
                indicatorColor = PurpleBackground.copy(alpha = 0.1f) // Warna indikator di belakang ikon
            ),
            onClick = {
                Toast.makeText(context, "Dashboard Mahasiswa", Toast.LENGTH_SHORT).show()
            }
        )
        NavigationBarItem(
            icon = {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(PurpleBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
            },
            selected = false,
            onClick = onMenuClick,
            // We can remove colors here as the styling is handled by the Box and Icon directly.
            // The indicator is not needed for this custom button.
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.DoorBack, contentDescription = "Logout") },
            selected = false,
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = PurpleBackground, // Ubah warna ikon tidak terpilih menjadi ungu
                unselectedTextColor = PurpleBackground // Ubah warna label tidak terpilih menjadi ungu
            ),
            onClick = {
                context.startActivity(Intent(context, LoginActivity::class.java))
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppMahasiswaDrawer(
    closeDrawer: () -> Unit,
    currentActivity: KClass<*>
) {
    val context = LocalContext.current

    // Menu structure - PERBAIKAN NAVIGASI
    val menuItems = listOf(
        ScreenMhs("Beranda", Icons.Default.Home, DashboardMahasiswaScreen::class),
        ScreenMhs("Progress Proyek", Icons.Default.FolderShared, DashboardMahasiswaScreen::class),
        ScreenMhs("Nilai Akhir", Icons.Default.Star,  DashboardMahasiswaScreen::class)
    )

    val logoutStructure = listOf(
        MenuSectionMhs(
            title = "Akun",
            isCollapsible = false, // Langsung tampilkan item
            initialExpanded = true, // Tidak relevan jika isCollapsible false
            items = listOf(
                ScreenMhs("Logout", Icons.Default.DoorBack, LoginActivity::class) // Navigasi ke LoginActivity
            )
        )
    )

    ModalDrawerSheet(
        drawerContainerColor = SidebarBackground,
        modifier = Modifier.fillMaxWidth(0.75f)
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "MonPAD",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = closeDrawer) {
                    Icon(
                        Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Close Menu",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                onValueChange = { },
                placeholder = { Text("Search", color = Color.LightGray) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.LightGray)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    unfocusedContainerColor = SidebarBackground,
                    focusedContainerColor = SidebarBackground,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            color = Color.White.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Menu Items
        Column(modifier = Modifier.weight(1f)) {
            Spacer(modifier = Modifier.height(16.dp))
            menuItems.forEach { item ->
                DrawerMahasiswaItem(
                    item = item,
                    isSelected = item.route.java == currentActivity.java,
                    onClick = {
                        closeDrawer()
                        context.startActivity(Intent(context, item.route.java))
                    }
                )
            }

            logoutStructure.forEach { section ->
                Spacer(modifier = Modifier.height(16.dp))

                if (section.isCollapsible) {
                    var expanded by remember { mutableStateOf(section.initialExpanded) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clickable { expanded = !expanded },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Person, contentDescription = section.title, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(section.title, fontWeight = FontWeight.SemiBold, color = Color.White)
                        }
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = if (expanded) "Collapse" else "Expand",
                            tint = Color.White
                        )
                    }

                    if (expanded) {
                        Spacer(modifier = Modifier.height(8.dp))
                        section.items.forEach { item ->
                            DrawerMahasiswaItem(
                                item = item, isSelected = false, // Logout tidak pernah 'selected'
                                onClick = {
                                    closeDrawer()
                                    context.startActivity(Intent(context, item.route.java))
                                }
                            )
                        }
                    }
                } else {
                    // Memberi space lebih untuk bagian Akun
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Person, contentDescription = section.title, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(section.title, fontWeight = FontWeight.SemiBold, color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    section.items.forEach { item ->
                        DrawerMahasiswaItem(
                            item = item, isSelected = false,
                            onClick = {
                                closeDrawer()
                                context.startActivity(Intent(context, item.route.java))
                            }
                        )
                    }
                }
            }
        }

        // Footer
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            HorizontalDivider(color = Color.White.copy(alpha = 0.3f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Mahasiswa", fontWeight = FontWeight.SemiBold, color = Color.White)
                    Text("TRPL", fontSize = 14.sp, color = Color.LightGray)
                }
            }
        }
    }
}

@Composable
fun DrawerMahasiswaItem(item: ScreenMhs, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color.White else SidebarBackground
    val contentColor = if (isSelected) SidebarBackground else Color.White

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(end = 16.dp)
            .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(start = 32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            item.icon,
            contentDescription = item.title,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            item.title,
            color = contentColor,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

// Dummy Activities untuk navigasi (Buat file terpisah untuk masing-masing)
class DataDosenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonpadTheme {
                // Isi dengan konten DataDosenScreen
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text("Data Dosen Screen", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

class DataMahasiswaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonpadTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text("Data Mahasiswa Screen", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

class DataAsistenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonpadTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text("Data Asisten Screen", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardMhsPreview() {
    MonpadTheme {
        DashboardMahasiswaContent()
    }
}