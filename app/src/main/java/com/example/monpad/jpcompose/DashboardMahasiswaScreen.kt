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
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monpad.MainActivity
import kotlin.reflect.KClass
import java.text.SimpleDateFormat
import com.example.monpad.jpcompose.ui.theme.Purple40
import com.example.monpad.network.DashboardGroup
import com.example.monpad.network.DashboardProject
import com.example.monpad.viewmodel.DashboardMahasiswaViewModel
import com.example.monpad.viewmodel.UiState
import android.util.Log
import com.example.monpad.NilaiAkhir
import com.example.monpad.ProgresProyek
import com.example.monpad.network.Grades
import java.text.DecimalFormat
import java.util.Locale

class DashboardMahasiswaScreen : ComponentActivity() {

    private val viewModel: DashboardMahasiswaViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonpadTheme {
                DashboardMahasiswaContent(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardMahasiswaContent(viewModel: DashboardMahasiswaViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentActivity = DashboardMahasiswaScreen::class

    val dashboardState by viewModel.dashboardState.collectAsState()
    val dproject by viewModel.dproject.collectAsState()
    val dgroup by viewModel.dgroup.collectAsState()
    val grade by viewModel.grade.collectAsState()
    val error by viewModel.error.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getDashboardData()
    }


    if (error != null) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

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
                            .clip(RoundedCornerShape(10.dp))
                            .padding(top = 25.dp, start = 4.dp, end = 4.dp)
                    ) {
                        NilaiCardSection(dproject = dproject, grade = grade)

                        Spacer(modifier = Modifier.height(50.dp))

                        ProjectInfoCard(dproject = dproject, dgroup = dgroup)

                    }
                }
            }
        }
    }
}


// Not fiction
@Composable
fun ProjectInfoCard(dproject: DashboardProject?, dgroup: DashboardGroup?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(8.dp, 40.dp))

            if (dproject == null) {
                // Tampilkan loading atau placeholder
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = "Memuat data project...",
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp)
                ) {
                    Text(
                        text = dproject.nama_projek ?: "Nama Project Tidak Tersedia",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Purple40,
                        modifier = Modifier.padding(bottom = 15.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Deskripsi: ",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = dproject.deskripsi ?: "Tidak ada deskripsi",
                        fontSize = 15.sp,
                        textAlign = TextAlign.Justify,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Column(modifier = Modifier.padding(start = 15.dp)) {
                        Text(
                            text = "Tahun Ajaran : ${dproject.tahun_ajaran ?: "N/A"}",
                            fontSize = 11.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Minggu ke : ${dproject.weekPeriod ?: "N/A"}",
                            fontSize = 11.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Jumlah Anggota : ${dgroup?.anggota?.size ?: "N/A"}",
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

@Composable
fun NilaiCardSection(
    dproject: DashboardProject?,
    grade: Grades?
) {

    val nilaiProyek = grade?.projectGrade.toString()
    val nilaiUAS = "-"
    val nilaiUTS = "-"
    val nilaiPersonal = grade?.personalGrade.toString()

    val finalGradeString = grade?.finalGrade.toString()
    // Pastikan nilainya adalah angka sebelum memformat
    val nilaiAkhir = finalGradeString.toDoubleOrNull()?.let {
        // Menggunakan String.format()
        String.format("%.2f", it)
    } ?: finalGradeString

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(PurpleBackgroundNilai)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            // Bagian Nilai Akhir
            Text(
                text = nilaiAkhir,
                fontSize = 72.sp, // Ukuran huruf besar untuk nilai utama
                fontWeight = FontWeight.Black,
                color = PurpleNilai
            )
            Text(
                text = "Nilai Akhir",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = PurpleNilai
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
                    NilaiDetailCard(
                        title = "Nilai Proyek",
                        value = nilaiProyek,
                        modifier = Modifier.weight(1f)
                    )
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
                    NilaiDetailCard(
                        title = "Nilai Personal",
                        value = nilaiPersonal,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun NilaiDetailCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxHeight()
            .width(130.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = PurpleCardNilaiSmall),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                color = PurpleNilai
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
        ScreenMhs("Beranda", Icons.Default.Home, MainActivity::class), // Navigasi ke MainActivity
        ScreenMhs("Progress Proyek", Icons.Default.FolderShared, ProgresProyek::class), // Navigasi ke Progres Proyek
        ScreenMhs("Nilai Akhir", Icons.Default.Star, NilaiAkhir::class), // Navigasi ke Nilai Akhir
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

//@Preview(showBackground = true)
//@Composable
//fun DashboardMhsPreview() {
//    MonpadTheme {
//        DashboardMahasiswaContent()
//    }
//}