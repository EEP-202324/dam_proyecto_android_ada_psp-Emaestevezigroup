package com.example.universidades.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Define los colores
val PurpleDark = Color(0xFF6200EE)
val PurpleGreyDark = Color(0xFF9E9E9E)
val PinkDark = Color(0xFFE91E63)

val PurpleLight = Color(0xFFBB86FC)
val PurpleGreyLight = Color(0xFFBDBDBD)
val PinkLight = Color(0xFFEC407A)

// Esquema de color oscuro
val DarkColorScheme = darkColorScheme(
    primary = PurpleDark,
    secondary = PurpleGreyDark,
    tertiary = PinkDark
)

// Esquema de color claro
val LightColorScheme = lightColorScheme(
    primary = PurpleLight, // Usando PurpleDark en lugar de Purple40
    secondary = PurpleGreyLight,
    tertiary = PinkLight
)
