package com.example.monpad.jpcompose.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Material Design Colors
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val PurpleCardNilaiSmall = Color(0x4DE5E0EB)


val PurpleNilai = Color(0xFF52357B)
val PurpleBackgroundNilai = Brush.linearGradient(
    colors = listOf(
        Color(0xFF9786B0),
        Color(0xFF7498BB)
    )
)
 // rgba(151, 134, 176, 0.7) - This is just the first color of the gradient as Jetpack Compose doesn't support gradients in a single color value. The second color is rgba(116, 152, 187, 0.7) -> 0xB37498BB



// Custom App Colors
val PurpleBackground = Color(0xFF5B4A8C) // Background utama aplikasi
val DarkHeader = Color(0xFF673AB7) // Header section
val CardPurple = Color(0xFF8B7BAA) // Indicator purple
val CardBlue = Color(0xFF6B9BD6) // Indicator blue
val SidebarBackground = Color(0xFF3D2F5C) // Sidebar/Drawer background