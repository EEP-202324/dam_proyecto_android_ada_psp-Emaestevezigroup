package com.example.universidades.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import com.example.universidades.models.Category
import com.example.universidades.models.Location
import com.example.universidades.models.University
import com.example.universidades.viewmodels.UniversityViewModel
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun AddUniversityScreen(navController: NavController, universityViewModel: UniversityViewModel) {
    val isLocationMenuExpanded = remember { mutableStateOf(false) }
    val isCategoryMenuExpanded = remember { mutableStateOf(false) }

    val idState = remember { mutableStateOf("") }
    val nameState = remember { mutableStateOf("") }
    val addressState = remember { mutableStateOf("") }
    val phoneNumberState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val locationState = remember { mutableStateOf("") }
    val categoryState = remember { mutableStateOf("") }
    val hasScholarshipState = remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val locations = listOf("Madrid", "Barcelona", "Sevilla", "Valencia", "Malaga")
    val categories = listOf("Ingenieria", "Arte", "Salud", "Ciencias", "Letras")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Agrega una nueva universidad", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text("Name") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = addressState.value,
            onValueChange = { addressState.value = it },
            label = { Text("Address") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = phoneNumberState.value,
            onValueChange = { phoneNumberState.value = it },
            label = { Text("Phone Number") }
        )

        if (phoneNumberState.value.length != 9) {
            Text(
                text = "El número de teléfono debe tener 9 dígitos",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") }
        )

        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        if (!emailRegex.matches(emailState.value)) {
            Text(
                text = "El formato del email no es correcto",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Scholarship:")
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = !hasScholarshipState.value,
                onClick = { hasScholarshipState.value = false },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.secondary
                ),
                modifier = Modifier.align(Alignment.CenterVertically) // Alinea verticalmente el radio button
            )
            Text(text = "No", modifier = Modifier.align(Alignment.CenterVertically))

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = hasScholarshipState.value,
                onClick = { hasScholarshipState.value = true },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.secondary
                ),
                modifier = Modifier.align(Alignment.CenterVertically) // Alinea verticalmente el radio button
            )
            Text(text = "Si", modifier = Modifier.align(Alignment.CenterVertically))
        }


        Spacer(modifier = Modifier.height(10.dp))


        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = if (locationState.value.isEmpty()) "- Localización -" else locationState.value,
                    modifier = Modifier
                        .clickable { isLocationMenuExpanded.value = !isLocationMenuExpanded.value }
                        .padding(vertical = 16.dp)
                        .weight(1f)
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
            Spacer(modifier = Modifier.height(10.dp))
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
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
                        .weight(1f)
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
            Spacer(modifier = Modifier.height(10.dp))
        }
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Button(
            onClick = {
                if (phoneNumberState.value.length != 9) {
                    return@Button
                }

                if (!emailRegex.matches(emailState.value)) {
                    return@Button
                }

                val university = University(
                    id = idState.value?.toLongOrNull(),
                    name = nameState.value,
                    address = addressState.value,
                    phoneNumber = phoneNumberState.value,
                    email = emailState.value,
                    hasScholarship = hasScholarshipState.value,
                    location = Location(
                        id = getLocationIdFromName(locationState.value),
                        name = locationState.value
                    ),
                    category = Category(
                        id = getCategoryIdFromName(categoryState.value),
                        name = categoryState.value
                    )
                )

                universityViewModel.createUniversity(university)
                navController.popBackStack()
            },
            modifier = Modifier,
            enabled = true,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
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
