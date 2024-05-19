package com.example.universidades.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color


val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val UniversityColors = lightColors(
    primary = Purple80,
    primaryVariant = Purple40,
    secondary = Pink80,
    secondaryVariant = Pink40,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = Color.Red,
    onError = Color.White
)

val DarkUniversityColors = darkColors(
    primary = Purple80,
    primaryVariant = Purple40,
    secondary = Pink80,
    secondaryVariant = Pink40,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    error = Color.Red,
    onError = Color.Black
)


