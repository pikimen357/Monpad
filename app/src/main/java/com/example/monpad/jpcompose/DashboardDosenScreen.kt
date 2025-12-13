package com.example.monpad.jpcompose

import android.content.Intent // Import untuk navigasi Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // Import semua ikon yang digunakan di Drawer
import androidx.compose.material3.*
import androidx.compose.runtime.* // Import untuk remember, mutableStateOf, dll.
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector // Digunakan oleh DrawerItem
import androidx.compose.ui.platform.LocalContext // Digunakan untuk Intent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monpad.jpcompose.ui.theme.MonpadTheme
import com.example.monpad.menuStructure // Import struktur menu dari DrawerItems.kt
import com.example.monpad.* // Import semua Activity (Dosen, Mahasiswa, ProjectActivity, Asisten, Screen)
import kotlin.reflect.KClass // Digunakan untuk currentActivity

import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monpad.jpcompose.ui.theme.Purple40
import com.example.monpad.viewmodel.DashboardDosenViewModel
import com.example.monpad.viewmodel.UiState
import kotlinx.coroutines.launch

// Define custom colors based on the image's theme
val PurpleBackground = Color(0xFF9370DB) // A medium purple for the overall background
val DarkHeader = Color(0xFF673AB7) // A dark purple for the header section
val CardPurple = Color(0xFF4B0082) // A deep purple for the circle indicators
val CardBlue = Color(0xFF1E90FF) // A bright blue for one of the indicators
val SidebarBackground = Color(0xFF673AB7) // Warna Sidebar (sama dengan DarkHeader)

// Di file DashboardDosenScreen.kt (Ubah nama kelas menjadi DashboardDosen)
class DashboardDosen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonpadTheme {
                DashboardDosenContent()
            }
        }
    }
}

// Main Composable that holds the entire screen structure
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardDosenContent(
    viewModel: DashboardDosenViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val dashboardState by viewModel.dashboardState.collectAsState()
    val dashboardDetail by viewModel.dshdetail.collectAsState()
    val uiState by viewModel.dashboardState.collectAsState()
    val error by viewModel.error.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getDashboardDosenData()
    }


    if (error != null) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    // Activity yang saat ini aktif (digunakan untuk menandai menu yang dipilih)
    val currentActivity = DashboardDosen::class

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                closeDrawer = {
                    scope.launch { drawerState.close() }
                },
                currentActivity = currentActivity
            )
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        Scaffold(
            bottomBar = { DashboardBottomNavBar() },
            modifier = Modifier
                .fillMaxSize()
                .background(PurpleBackground)
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .background(PurpleBackground)
            ) {
                // 1. Header Section (Sekarang menerima fungsi onMenuClick)
                HeaderSection(
                    onMenuClick = {
                        scope.launch { drawerState.open() }
                    }
                )

                // 2. Main Content Cards
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp, bottom = 30.dp)
                ) {
                    StatCard(indicatorColor = CardPurple, count = dashboardDetail?.jumlah_mahasiswa.toString(), label = "Mahasiswa Terdaftar")
                    Spacer(modifier = Modifier.height(16.dp))
                    StatCard(indicatorColor = CardBlue, count = dashboardDetail?.jumlah_asisten.toString(), label = "Asisten Praktikum")
                    Spacer(modifier = Modifier.height(16.dp))
                    StatCard(indicatorColor = CardPurple, count = dashboardDetail?.jumlah_projek.toString(), label = "Proyek Aktif")
                    Spacer(modifier = Modifier.height(16.dp))
                    StatCard(indicatorColor = CardPurple, count = dashboardDetail?.rata_rata.toString(), label = "Progres Nilai Kelas")
                    Spacer(modifier = Modifier.height(32.dp))
                    ProjectsAndGroupsSection()
                }
            }
        }
    }
}

// --- Composable Components (Header, Cards, Bottom Bar) ---

@Composable
fun HeaderSection(onMenuClick: () -> Unit) {
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
                        text = "Dashboard Dosen",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Beranda > Dashboard Dosen",
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

// (StatCard, ProjectsAndGroupsSection, DashboardBottomNavBar tetap sama)
// ... (Kode StatCard, ProjectsAndGroupsSection, DashboardBottomNavBar) ...

@Composable
fun StatCard(indicatorColor: Color, count: String, label: String) {
    Card(
        modifier = Modifier.height(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp, 130.dp)
                    .background(indicatorColor, RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = count,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = label,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
@Composable
fun ProjectsAndGroupsSection() {
    val context = LocalContext.current
    Card(
        modifier = Modifier.height(200.dp),
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
            Box(
                modifier = Modifier
                    .size(8.dp, 40.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Proyek & Kelompok",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Purple40,
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        context.startActivity(Intent(context, ProjectActivity::class.java))
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Purple40)
                ) {
                    Text(
                        text = "Lihat Detail",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }


            }


        }
    }
}
@Composable
fun DashboardBottomNavBar() {
    // Calling the renamed composable from the other file.
    // Note: The original file for BottomNavigationBar had hardcoded intents.
    // These were removed in the refactor to make the component more reusable.
    // The correct navigation logic should be passed via onClick parameters if needed.
    BottomNavigationBar()
}

// --- KOMPONEN SIDEBAR (AppDrawer dan DrawerItem) ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    closeDrawer: () -> Unit,
    currentActivity: KClass<*>
) {
    val context = LocalContext.current

    ModalDrawerSheet(
        drawerContainerColor = SidebarBackground,
        modifier = Modifier.fillMaxWidth(0.75f)
    ) {
        // --- 1. Header (Logo MonPAD & Search) ---
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
                onValueChange = { /* */ },
                placeholder = { Text("Search", color = Color.LightGray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    unfocusedContainerColor = SidebarBackground,
                    focusedContainerColor = SidebarBackground,
                    cursorColor = Color.White
                ),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.White.copy(alpha = 0.3f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

        // --- 2. Menu Item List ---
        Column(modifier = Modifier.weight(1f)) {
            menuStructure.forEach { section ->
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
                            Icon(Icons.Default.GridView, contentDescription = section.title, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(section.title, fontWeight = FontWeight.SemiBold, color = Color.White)
                        }
                        Icon(
                            if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = if (expanded) "Collapse" else "Expand",
                            tint = Color.White
                        )
                    }

                    if (expanded) {
                        Spacer(modifier = Modifier.height(8.dp))
                        section.items.forEach { item ->
                            DrawerItem(
                                item = item,
                                isSelected = item.route.java == currentActivity.java,
                                onClick = {
                                    closeDrawer()
                                    if (item.title == "Log Out") {
                                        // TODO: Implementasi Log Out
                                        println("Log Out clicked")
                                    } else {
                                        context.startActivity(Intent(context, item.route.java))
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        // --- 3. Footer (Dosen Info) ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clipToBounds()
        ) {
            Divider(color = Color.White.copy(alpha = 0.3f), thickness = 1.dp)
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
                    Text("Nama Dosen", fontWeight = FontWeight.SemiBold, color = Color.White)
                    Text("Dosen", fontSize = 14.sp, color = Color.LightGray)
                }
            }
        }
    }
}

@Composable
fun DrawerItem(item: Screen, isSelected: Boolean, onClick: () -> Unit) {
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

@Preview(showBackground = true)
@Composable
fun DashboardDosenPreview() {
    MonpadTheme {
        DashboardDosenContent()
    }
}