package com.example.monpad.jpcompose

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoorBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.monpad.LoginActivity
import com.example.monpad.ProjectActivity

@Composable
fun BottomNavigationBar() {
    val context = LocalContext.current
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
    ) {
        NavigationBarItem(
            selected = false,
            onClick = {
                context.startActivity(Intent(context, DashboardDosen::class.java))
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Dashboard",
                    tint = Color.Gray
                )
            }
        )

        NavigationBarItem(
            selected = true,
            onClick = {
                context.startActivity(Intent(context, ProjectActivity::class.java))
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White,
                    modifier = Modifier
                        .background(Color(0xFF6B4BA6), RoundedCornerShape(50))
                        .padding(10.dp)
                        .size(19.dp)
                )
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                context.startActivity(Intent(context, LoginActivity::class.java))
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.DoorBack,
                    contentDescription = "Login",
                    tint = Color.Gray
                )
            }
        )
    }
}

@Composable
fun BottomNavigationBarAst() {
    val context = LocalContext.current
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
    ) {
        NavigationBarItem(
            selected = false,
            onClick = {
                context.startActivity(Intent(context, DashboardDosen::class.java))
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Dashboard",
                    tint = Color.Gray
                )
            }
        )

        NavigationBarItem(
            selected = true,
            onClick = {
                context.startActivity(Intent(context, ProjectActivity::class.java))
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White,
                    modifier = Modifier
                        .background(Color(0xFF6B4BA6), RoundedCornerShape(50))
                        .padding(10.dp)
                        .size(19.dp)
                )
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                context.startActivity(Intent(context, LoginActivity::class.java))
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.DoorBack,
                    contentDescription = "Login",
                    tint = Color.Gray
                )
            }
        )
    }
}