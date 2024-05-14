package com.example.universidades.ui.Screen
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddUniversityScreen(onUniversityAdded: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add University Screen")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onUniversityAdded() }) {
            Text(text = "Add University")
        }
    }
}
