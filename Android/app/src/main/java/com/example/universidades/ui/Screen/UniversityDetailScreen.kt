package com.example.universidades.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.universidades.models.University

@Composable
fun UniversityDetailScreen(
    university: University,
) {
    Column(Modifier.padding(16.dp)) {
        Text(text = "University ID: ${university.id ?: "N/A"}")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Name: ${university.name}")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Address: ${university.address}")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Phone Number: ${university.phoneNumber}")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Email: ${university.email}")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Scholarship: ${university.hasScholarship}")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Location: ${university.location.name}")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Category: ${university.category.name}")
    }
}
