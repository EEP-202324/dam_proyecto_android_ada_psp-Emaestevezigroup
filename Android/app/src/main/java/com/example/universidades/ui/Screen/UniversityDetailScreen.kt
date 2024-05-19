package com.example.universidades.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.universidades.models.University

@Composable
fun UniversityDetailScreen(
    university: University,
    onUpdateUniversityClicked: (University) -> Unit,
) {
    var newName by remember { mutableStateOf(university.name) }
    var newAddress by remember { mutableStateOf(university.address) }
    var newPhoneNumber by remember { mutableStateOf(university.phoneNumber) }
    var newEmail by remember { mutableStateOf(university.email) }
    var newHasScholarship by remember { mutableStateOf(university.hasScholarship) }

    var snackbarVisibleState by remember { mutableStateOf(false) }

    Column(Modifier.padding(16.dp)) {
        Text(text = "University ID: ${university.id ?: "N/A"}")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Name:")
        TextField(
            value = newName,
            onValueChange = { newName = it }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Address:")
        TextField(
            value = newAddress,
            onValueChange = { newAddress = it }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Phone Number:")
        TextField(
            value = newPhoneNumber,
            onValueChange = { newPhoneNumber = it }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Email:")
        TextField(
            value = newEmail,
            onValueChange = { newEmail = it }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = newHasScholarship,
                onCheckedChange = { newHasScholarship = it }
            )
            Text(text = "Has Scholarship")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            val updatedUniversity = University(
                id = university.id,
                name = newName,
                address = newAddress,
                phoneNumber = newPhoneNumber,
                email = newEmail,
                hasScholarship = newHasScholarship,
                location = university.location,
                category = university.category
            )
            onUpdateUniversityClicked(updatedUniversity)
            snackbarVisibleState = true
        }) {
            Text("Guardar Cambios")
        }

        if (snackbarVisibleState) {
            Snackbar(
                action = {
                    Button(onClick = { snackbarVisibleState = false }) {
                        Text("OK")
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Cambios guardados correctamente")
            }
        }
    }
}