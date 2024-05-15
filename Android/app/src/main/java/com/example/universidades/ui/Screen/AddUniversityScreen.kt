package com.example.universidades.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import com.example.universidades.models.Category
import com.example.universidades.models.Location
import com.example.universidades.models.University
import com.example.universidades.viewmodels.UniversityViewModel

@Composable
fun AddUniversityScreen(navController: NavController, universityViewModel: UniversityViewModel) {
    val isLocationMenuExpanded = remember { mutableStateOf(false) }
    val isCategoryMenuExpanded = remember { mutableStateOf(false) }

    val nameState = remember { mutableStateOf("") }
    val addressState = remember { mutableStateOf("") }
    val phoneNumberState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val locationState = remember { mutableStateOf("") }
    val categoryState = remember { mutableStateOf("") }
    val hasScholarshipState = remember { mutableStateOf(false) }

    val locations = listOf("Madrid", "Barcelona", "Sevilla", "Valencia", "Malaga")
    val categories = listOf("Ingenieria", "Arte", "Salud", "Ciencias", "Letras")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Agrega una nueva universidad", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text("Name") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = addressState.value,
            onValueChange = { addressState.value = it },
            label = { Text("Address") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = phoneNumberState.value,
            onValueChange = { phoneNumberState.value = it },
            label = { Text("Phone Number") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.padding(vertical = 8.dp), // Ajuste del margen izquierdo
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Scholarship:")
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = hasScholarshipState.value,
                onClick = { hasScholarshipState.value = true },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.secondary
                )
            )
            Text(text = "Si")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = !hasScholarshipState.value,
                onClick = { hasScholarshipState.value = false },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.secondary
                )
            )
            Text(text = "No")
        }

        Spacer(modifier = Modifier.height(16.dp))


        Column(
            modifier = Modifier.fillMaxWidth(), // Asegurar que el ancho del Column sea igual al ancho del contenedor padre
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp)) // Espacio a la izquierda del texto
                Text(
                    text = if (locationState.value.isEmpty()) "- Localización -" else locationState.value,
                    modifier = Modifier
                        .clickable { isLocationMenuExpanded.value = !isLocationMenuExpanded.value }
                        .padding(vertical = 16.dp)
                        .weight(1f) // El texto ocupa el espacio restante
                )
            }
            if (isLocationMenuExpanded.value) {
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = { isLocationMenuExpanded.value = false },
                    modifier = Modifier.background(MaterialTheme.colors.surface)
                ) {
                    locations.forEach { location ->
                        DropdownMenuItem(onClick = {
                            locationState.value = location
                            isLocationMenuExpanded.value = false
                        }) {
                            Text(location)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(), // Asegurar que el ancho del Column sea igual al ancho del contenedor padre
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp)) // Espacio a la izquierda del texto
                Text(
                    text = if (categoryState.value.isEmpty()) "- Categoría -" else categoryState.value,
                    modifier = Modifier
                        .clickable { isCategoryMenuExpanded.value = !isCategoryMenuExpanded.value }
                        .padding(vertical = 16.dp)
                        .weight(1f) // El texto ocupa el espacio restante
                )
            }
            if (isCategoryMenuExpanded.value) {
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = { isCategoryMenuExpanded.value = false },
                    modifier = Modifier.background(MaterialTheme.colors.surface)
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(onClick = {
                            categoryState.value = category
                            isCategoryMenuExpanded.value = false
                        }) {
                            Text(category)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }


        Button(onClick = {
            val locationId = getLocationIdFromName(locationState.value)
            val categoryId = getCategoryIdFromName(categoryState.value)

            val university = University(
                name = nameState.value,
                address = addressState.value,
                phoneNumber = phoneNumberState.value,
                email = emailState.value,
                hasScholarship = hasScholarshipState.value, // Asignar el valor de la beca
                location = Location(id = locationId, name = locationState.value),
                category = Category(id = categoryId, name = categoryState.value)
            )

            universityViewModel.createUniversity(university)
            navController.popBackStack()
        }) {
            Text("Agregar")
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
    return locationsMap[locationName] ?: -1L
}

fun getCategoryIdFromName(categoryName: String): Long {
    val categoriesMap = mapOf(
        "Ingenieria" to 1L,
        "Arte" to 2L,
        "Salud" to 3L,
        "Ciencias" to 4L,
        "Letras" to 5L
    )
    return categoriesMap[categoryName] ?: -1L
}
