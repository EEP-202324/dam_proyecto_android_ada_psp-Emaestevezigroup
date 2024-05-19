package com.example.universidades.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.universidades.viewmodels.UniversityViewModel

@Composable
fun SearchScreen(navController: NavHostController, universityViewModel: UniversityViewModel) {
    var id by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("University ID") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                universityViewModel.searchUniversities(id, category, location)
                navController.navigate("search_results")
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }
    }
}
