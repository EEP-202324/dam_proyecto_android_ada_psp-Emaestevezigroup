package com.example.universidades.models

import com.google.gson.annotations.SerializedName

data class University(
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("hasScholarship") val hasScholarship: Boolean = false,
    @SerializedName("location") val location: Location,
    @SerializedName("category") val category: Category
)
