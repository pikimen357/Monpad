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
import java.util.Locale
import com.example.monpad.jpcompose.ui.theme.Purple40
import com.example.monpad.network.DashboardGroup
import com.example.monpad.network.DashboardProject
import com.example.monpad.viewmodel.DashboardMahasiswaViewModel
import com.example.monpad.viewmodel.UiState
import android.util.Log
import com.example.monpad.network.Grades

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

class ProgressProyekScreen : ComponentActivity() {

    private val viewModel: DashboardMahasiswaViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonpadTheme {
                ProgresProyekContent(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgresProyekContent(viewModel: DashboardMahasiswaViewModel) {
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
                HeaderProgresProyekSection(
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

                        ProjectInfoCard(dproject = dproject, dgroup = dgroup)

                    }
                }
            }
        }
    }
}

@Composable
fun HeaderProgresProyekSection(onMenuClick: () -> Unit) {
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
                        text = "Progres Proyek",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Beranda > Progres Proyek",
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









