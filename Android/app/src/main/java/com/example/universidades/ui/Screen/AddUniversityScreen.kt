package com.example.universidades.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.universidades.models.Category
import com.example.universidades.models.Location
import com.example.universidades.models.University

@Composable
fun AddUniversityScreen(navController: NavController) {
    // Estados para los campos del formulario
    var nameState by remember { mutableStateOf("") }
    var addressState by remember { mutableStateOf("") }
    var phoneNumberState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var hasScholarshipState by remember { mutableStateOf(false) }
    var locationState by remember { mutableStateOf("Location") }
    var categoryState by remember { mutableStateOf("Category") }

    // Opciones para los menús desplegables
    val locations = listOf("Madrid", "Barcelona", "Sevilla", "Valencia", "Malaga")
    val categories = listOf("Ingenieria", "Arte", "Salud", "Ciencias", "Letras")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo de nombre de la universidad
        TextField(
            value = nameState,
            onValueChange = { nameState = it },
            label = { Text("Name") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de dirección
        TextField(
            value = addressState,
            onValueChange = { addressState = it },
            label = { Text("Address") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de número de teléfono
        TextField(
            value = phoneNumberState,
            onValueChange = { phoneNumberState = it },
            label = { Text("Phone Number") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de correo electrónico
        TextField(
            value = emailState,
            onValueChange = { emailState = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de beca
        Button(onClick = { hasScholarshipState = !hasScholarshipState }) {
            Text(if (hasScholarshipState) "Has Scholarship: Yes" else "Has Scholarship: No")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menú desplegable de localización
        DropdownMenu(
            expanded = locationState != "Location",
            onDismissRequest = { locationState = "Location" }
        ) {
            locations.forEach { location ->
                DropdownMenuItem(onClick = { locationState = location }) {
                    Text(location)
                }
            }
        }
        Button(onClick = { /* Empty lambda */ }) {
            Text(locationState)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menú desplegable de categoría
        DropdownMenu(
            expanded = categoryState != "Category",
            onDismissRequest = { categoryState = "Category" }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(onClick = { categoryState = category }) {
                    Text(category)
                }
            }
        }
        Button(onClick = { /* Empty lambda */ }) {
            Text(categoryState)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para enviar el formulario
        Button(onClick = {
            val locationId = getLocationIdFromName(locationState)
            val categoryId = getCategoryIdFromName(categoryState)

            val university = University(
                name = nameState,
                address = addressState,
                phoneNumber = phoneNumberState,
                email = emailState,
                hasScholarship = hasScholarshipState,
                location = Location(id = locationId, name = locationState),
                category = Category(id = categoryId, name = categoryState)
            )

            // Aquí puedes manejar la lógica para enviar la universidad al backend o realizar otras acciones
        }) {
            Text("Submit")
        }
    }
}

fun getLocationIdFromName(locationName: String): Long {
    val locationsMap = mapOf(
        "Madrid" to 1L,
        "Barcelona" to 2L,
        "Sevilla" to 3L,
        "Valencia" to 4L,
        "Malaga" to 5L
    )
    return locationsMap[locationName] ?: -1L // Devuelve -1 si no se encuentra la ubicación
}

fun getCategoryIdFromName(categoryName: String): Long {
    val categoriesMap = mapOf(
        "Ingenieria" to 1L,
        "Arte" to 2L,
        "Salud" to 3L,
        "Ciencias" to 4L,
        "Letras" to 5L
    )
    return categoriesMap[categoryName] ?: -1L // Devuelve -1 si no se encuentra la categoría
}
