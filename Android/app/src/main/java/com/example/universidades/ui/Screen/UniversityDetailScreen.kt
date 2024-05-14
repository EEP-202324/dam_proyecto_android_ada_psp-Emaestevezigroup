package com.example.universidades.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UniversityDetailScreen(universityId: String) {
    // Aquí deberías obtener los detalles de la universidad con universityId
    // Por ejemplo, puedes usar el ID para buscar la universidad en tu base de datos o en tu repositorio
    // Luego puedes mostrar los detalles de la universidad en esta pantalla

    // Temporalmente, solo mostraremos el ID de la universidad para demostración
    Text(text = "University ID: $universityId", modifier = Modifier.padding(16.dp))
}
