package com.example.universidades.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UniversityDetailScreen(
    universityId: Long,
    name: String,
    address: String,
    phoneNumber: String,
    email: String,
    hasScholarship: Boolean,
    location: String,
    category: String
) {
    Column(Modifier.padding(16.dp)) {
        Text(text = "University ID: $universityId")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Name: $name")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Address: $address")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Phone Number: $phoneNumber")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Email: $email")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Scholarship: $hasScholarship")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Location: $location")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Category: $category")
    }
}
