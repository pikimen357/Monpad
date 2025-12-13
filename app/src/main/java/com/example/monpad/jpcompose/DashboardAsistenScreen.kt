package com.example.monpad.jpcompose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.monpad.LoginActivity
import com.example.monpad.NilaiActivity
import com.example.monpad.ProjectActivity
import com.example.monpad.jpcompose.ui.theme.*
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.ui.text.font.FontWeight
import com.example.monpad.Mahasiswa
import com.example.monpad.MainActivity
import com.example.monpad.ProjectAsistenActivity
import kotlin.reflect.KClass
import com.example.monpad.jpcompose.ui.theme.Purple40
import com.example.monpad.viewmodel.DashboardAsistenViewModel
import com.example.monpad.viewmodel.UiState

// Data class untuk menu item
data class ScreenAst(
    val title: String,
    val icon: ImageVector,
    val route: KClass<out ComponentActivity>
)

// Data class untuk menu section
data class MenuSectionAst(
    val title: String,
    val items: List<ScreenAst>,
    val isCollapsible: Boolean = false,
    val initialExpanded: Boolean = false
)

class DashboardAsisten : ComponentActivity() {

    private val viewModel: DashboardAsistenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonpadTheme {
                DashboardAssitenContent(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardAssitenContent(
viewModel: DashboardAsistenViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentActivity = DashboardAsisten::class
    val context = LocalContext.current

    val dashboardState by viewModel.dashboardState.collectAsState()
    val dashboardAsisten by viewModel.dshAstDetail.collectAsState()
    val error by viewModel.error.collectAsState()

//    LaunchedEffect(dashboardState) {
//        when (val state = dashboardState) {
//            is UiState.Loading -> {
//                Toast.makeText(context, "Loading dashboard data...", Toast.LENGTH_SHORT).show()
//            }
//            is UiState.Success -> {
//                Toast.makeText(context, "Data loaded successfully!", Toast.LENGTH_SHORT).show()
//                Log.d("DashboardUI", "Jumlah Mahasiswa: ${dashboardAsisten?.jumlah_mahasiswa}")
//            }
//            is UiState.Error -> {
//                Toast.makeText(context, "Error: ${state.message}", Toast.LENGTH_LONG).show()
//            }
//            else -> {}
//        }
//    }

    LaunchedEffect(Unit) {
        viewModel.getDashboardAsistenData()
    }


    if (error != null) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppMhsDrawer(
                closeDrawer = { scope.launch { drawerState.close() } },
                currentActivity = currentActivity
            )
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigationBarAst(
                    // Panggil fungsi untuk membuka drawer saat tombol menu di bottom bar diklik
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .background(PurpleBackground)
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(PurpleBackground)
            ) {
                HeaderMhsSection(
                    onMenuClick = { scope.launch { drawerState.open() } }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 50.dp)
                ) {
                    StatMhsCard(
                        indicatorColor = CardPurple,
                        count = dashboardAsisten?.jumlah_mahasiswa.toString(),
                        label = "Mahasiswa Terdaftar"
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    StatMhsCard(
                        indicatorColor = CardPurple,
                        count = dashboardAsisten?.rata_rata.toString(),
                        label = "Rata Rata Progres"
                    )

                    Spacer(modifier = Modifier.height(50.dp))

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
                                        context.startActivity(Intent(context,
                                            ProjectAsistenActivity::class.java))
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
            }
        }
    }
}

@Composable
fun HeaderMhsSection(onMenuClick: () -> Unit) {
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
                        text = "Dashboard Asisten",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Beranda > Dashboard Asisten",
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
fun StatMhsCard(indicatorColor: Color, count: String, label: String) {
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
fun ProjectGroupCard(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun BottomNavigationBarAst(onMenuClick: () -> Unit) {
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
                Toast.makeText(context, "Dashboard Asisten", Toast.LENGTH_SHORT).show()
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
fun AppMhsDrawer(
    closeDrawer: () -> Unit,
    currentActivity: KClass<*>
) {
    val context = LocalContext.current

    // Menu structure - PERBAIKAN NAVIGASI
    val menuItems = listOf(
        ScreenAst("Beranda", Icons.Default.Home, MainActivity::class),
        ScreenAst("Proyek & Kelompok", Icons.Default.FolderShared, ProjectActivity::class),
        ScreenAst("Data Mahasiswa", Icons.Default.People, Mahasiswa::class)
    )

    val logoutStructure = listOf(
        MenuSectionAst(
            title = "Akun",
            isCollapsible = false, // Langsung tampilkan item
            initialExpanded = true, // Tidak relevan jika isCollapsible false
            items = listOf(
                ScreenAst("Logout", Icons.Default.DoorBack, LoginActivity::class) // Navigasi ke LoginActivity
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
                DrawerMhsItem(
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
                            DrawerMhsItem(
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
                        DrawerMhsItem(
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
                    Text("Asisten", fontWeight = FontWeight.SemiBold, color = Color.White)
                    Text("TRPL", fontSize = 14.sp, color = Color.LightGray)
                }
            }
        }
    }
}

@Composable
fun DrawerMhsItem(item: ScreenAst, isSelected: Boolean, onClick: () -> Unit) {
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



//@Preview(showBackground = true)
//@Composable
//fun DashboarAstPreview() {
//
//    MonpadTheme {
//        DashboardAssitenContent()
//    }
//}