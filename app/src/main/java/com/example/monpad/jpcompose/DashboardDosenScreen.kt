package com.example.monpad.jpcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monpad.jpcompose.ui.theme.MonpadTheme // Assuming MonpadTheme is in this location

// Define custom colors based on the image's theme
val PurpleBackground = Color(0xFF9370DB) // A medium purple for the overall background
val DarkHeader = Color(0xFF673AB7) // A dark purple for the header section
val CardPurple = Color(0xFF4B0082) // A deep purple for the circle indicators
val CardBlue = Color(0xFF1E90FF) // A bright blue for one of the indicators

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
@Composable
fun DashboardDosenContent() {
    Scaffold(
        bottomBar = { DashboardBottomNavBar() },
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleBackground) // Set the main purple background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(PurpleBackground)
        ) {
            // 1. Header Section
            HeaderSection()

            // 2. Main Content Cards (scrollable, though not strictly needed for this layout)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp) // Spacing below the header
            ) {
                // Card 1: Mahasiswa Terdaftar (Registered Students)
                StatCard(
                    indicatorColor = CardPurple,
                    count = "XXX",
                    label = "Mahasiswa Terdaftar"
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Card 2: Asisten Praktikum (Lab Assistants)
                StatCard(
                    indicatorColor = CardBlue,
                    count = "X",
                    label = "Asisten Praktikum"
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Card 3: Proyek Aktif (Active Projects)
                StatCard(
                    indicatorColor = CardPurple,
                    count = "XX",
                    label = "Proyek Aktif"
                )
                Spacer(modifier = Modifier.height(32.dp))

                // 4. Proyek & Kelompok Section (Projects & Groups)
                ProjectsAndGroupsSection()
            }
        }
    }
}

// --- Composable Components ---

@Composable
fun HeaderSection() {
    // The header has a background that spans the width and is rounded at the bottom
    Box(
        modifier = Modifier
            .fillMaxWidth()
            // Clip the top area to match the look in the image (rounded corners at the bottom)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(Color.White)
            .padding(16.dp)
            .height(70.dp), // Fixed height for a distinct header
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
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
            IconButton(onClick = { /* Handle settings click */ }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.Black // Adjust color as needed
                )
            }
        }
    }
}

@Composable
fun StatCard(indicatorColor: Color, count: String, label: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circle Indicator
            Box(
                modifier = Modifier
                    .size(56.dp) // Size of the circle
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(indicatorColor)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = count,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text Content
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ProjectsAndGroupsSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Proyek & Kelompok",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Lihat Button
            Button(
                onClick = { /* Handle Lihat (View) click */ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkHeader), // Use DarkHeader for the button color
                modifier = Modifier
                    .fillMaxWidth(0.6f) // Make the button wide but not full width
                    .height(50.dp)
            ) {
                Text(
                    text = "Lihat",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun DashboardBottomNavBar() {
    // The Bottom Bar from the image, typically using BottomAppBar or custom Box
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White) // White background for the bar itself
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        // This is a placeholder for the actual navigation icons.
        // It should contain a Row with IconButton for each navigation item (Person, Menu, Arrow).
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder Icons
            // Icon 1 (Person)
            Box(modifier = Modifier.size(40.dp).padding(4.dp).clip(androidx.compose.foundation.shape.CircleShape))

            // Icon 2 (Menu/Center) - The large central button
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .offset(y = (-10).dp) // Offset to push it slightly up, as shown
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(DarkHeader),
                contentAlignment = Alignment.Center
            ) {
                // This would be the "Menu" icon, here represented by a centered line
                Divider(
                    color = Color.White,
                    modifier = Modifier.width(30.dp),
                    thickness = 3.dp
                )
            }

            // Icon 3 (Arrow/Exit)
            Box(modifier = Modifier.size(40.dp).padding(4.dp).clip(androidx.compose.foundation.shape.CircleShape))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DashboardDosenPreview() {
    MonpadTheme {
        DashboardDosenContent()
    }
}