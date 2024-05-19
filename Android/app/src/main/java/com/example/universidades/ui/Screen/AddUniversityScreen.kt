package com.example.universidades.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.universidades.models.Category
import com.example.universidades.models.Location
import com.example.universidades.models.University
import androidx.compose.ui.platform.LocalDensity
import com.example.universidades.viewmodels.UniversityViewModel


@Composable
fun AddUniversityScreen(
    navController: NavController,
    universityViewModel: UniversityViewModel,
    university: University?,
) {
    val locations by universityViewModel.locations.observeAsState(initial = emptyList())
    val categories by universityViewModel.categories.observeAsState(initial = emptyList())

    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var hasScholarship by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp), // Agregar espacio vertical entre cada elemento del formulario
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Agrega una nueva universidad", style = MaterialTheme.typography.h6)

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") }
        )

        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") }
        )

        if (phoneNumber.length != 9) {
            Text(
                text = "El número de teléfono debe tener 9 dígitos",
                color = MaterialTheme.colors.error
            )
        }

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        if (!emailRegex.matches(email)) {
            Text(
                text = "El formato del email no es correcto",
                color = MaterialTheme.colors.error
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Scholarship:")
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = !hasScholarship,
                onClick = { hasScholarship = false },
                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.secondary)
            )
            Text(text = "No")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = hasScholarship,
                onClick = { hasScholarship = true },
                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.secondary)
            )
            Text(text = "Sí")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 35.dp),
            ) {
                DropdownTextField(
                    label = "Location",
                    value = location,
                    onValueChange = { location = it },
                    items = locations.map { it.name }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 35.dp),
            ) {
                DropdownTextField(
                    label = "Category",
                    value = category,
                    onValueChange = { category = it },
                    items = categories.map { it.name }
                )
            }
        }


        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error
            )
        }

        Button(
            onClick = {
                errorMessage = null

                if (phoneNumber.length != 9) {
                    errorMessage = "El número de teléfono debe tener 9 dígitos"
                    return@Button
                }

                if (!emailRegex.matches(email)) {
                    errorMessage = "El formato del email no es correcto"
                    return@Button
                }

                val universityToAdd = University(
                    id = id.toLongOrNull(),
                    name = name,
                    address = address,
                    phoneNumber = phoneNumber,
                    email = email,
                    hasScholarship = hasScholarship,
                    location = Location(
                        id = getLocationIdFromName(location),
                        name = location
                    ),
                    category = Category(
                        id = getCategoryIdFromName(category),
                        name = category
                    )
                )

                universityViewModel.createUniversity(universityToAdd)

                navController.popBackStack()
            },
            enabled = true
        ) {
            Text(
                text = "Agregar",
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
fun DropdownTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    items: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    val textWidth = with(LocalDensity.current) {
        (0.dp).toPx()
    }

    Column {
        Text(label)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = { expanded = !expanded })
        ) {
            Text(
                text = value,
                modifier = Modifier.width(IntrinsicSize.Min)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
        Box(
            modifier = Modifier.width(textWidth.dp)
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        expanded = false
                        onValueChange(item)
                    }) {
                        Text(text = item)
                    }
                }
            }
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
