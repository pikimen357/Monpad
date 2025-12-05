package com.example.monpad.jpcompose

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monpad.NilaiActivity
import com.example.monpad.ProjectActivity
import com.example.monpad.jpcompose.ui.theme.*
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

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
fun DashboardAssitenContent() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentActivity = DashboardAsisten::class

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
            bottomBar = { BottomNavigationBar() },
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
                        .padding(top = 16.dp)
                ) {
                    StatMhsCard(
                        indicatorColor = CardPurple,
                        count = "XXX",
                        label = "Mahasiswa Terdaftar"
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    StatMhsCard(
                        indicatorColor = CardBlue,
                        count = "X",
                        label = "Asisten Praktikum"
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    StatMhsCard(
                        indicatorColor = CardPurple,
                        count = "XX",
                        label = "Proyek Aktif"
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    ProjectsAndGroupsSectionMhs()
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
        modifier = Modifier.fillMaxWidth(),
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
                    .size(8.dp, 40.dp)
                    .background(indicatorColor, RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = count,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
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
fun ProjectsAndGroupsSectionMhs() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ProjectGroupCard(
            title = "XX",
            subtitle = "Proyek Aktif",
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        ProjectGroupCard(
            title = "XX",
            subtitle = "Grup Mahasiswa",
            modifier = Modifier.weight(1f)
        )
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
fun BottomNavigationBarMhs() {
    NavigationBar(
        containerColor = Color.White,
        contentColor = PurpleBackground,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = true,
            onClick = { /* Navigate to Home */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = { /* Navigate to Profile */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Notifications") },
            label = { Text("Notif") },
            selected = false,
            onClick = { /* Navigate to Notifications */ }
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
    val menuStructure = listOf(
        MenuSectionAst(
            title = "Menu",
            isCollapsible = true,
            initialExpanded = false,
            items = listOf(
                ScreenAst("Beranda", Icons.Default.Home, DashboardAsisten::class),
                ScreenAst("Proyek & Kelompok", Icons.Default.FolderShared, ProjectActivity::class),
                ScreenAst("Nilai Mahasiswa", Icons.Default.People, NilaiActivity::class)
            )
        )
    )

    val logoutStructure = listOf(
        MenuSectionAst(
            title = "Akun",
            isCollapsible = true,
            initialExpanded = false,
            items = listOf(
                ScreenAst("Logout", Icons.Default.DoorBack, DashboardAsisten::class)
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
                            DrawerMhsItem(
                                item = item,
                                isSelected = item.route.java == currentActivity.java,
                                onClick = {
                                    closeDrawer()
                                    try {
                                        context.startActivity(Intent(context, item.route.java))
                                    } catch (e: Exception) {
                                        // Handle error jika Activity tidak ditemukan
                                        println("Error navigating to ${item.title}: ${e.message}")
                                    }
                                }
                            )
                        }
                    }
                }
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
                            DrawerMhsItem(
                                item = item,
                                isSelected = item.route.java == currentActivity.java,
                                onClick = {
                                    closeDrawer()
                                    try {
                                        context.startActivity(Intent(context, item.route.java))
                                    } catch (e: Exception) {
                                        // Handle error jika Activity tidak ditemukan
                                        println("Error navigating to ${item.title}: ${e.message}")
                                    }
                                }
                            )
                        }
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
                    Text("Nama Mahasiswa", fontWeight = FontWeight.SemiBold, color = Color.White)
                    Text("Mahasiswa", fontSize = 14.sp, color = Color.LightGray)
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
        DashboardAssitenContent()
    }
}