package com.example.universidades.models

data class University(
    val id: Long,
    val name: String,
    val address: String,
    val phoneNumber: String,
    val email: String,
    val hasScholarship: Boolean,
    val location: Location,
    val category: Category
)

