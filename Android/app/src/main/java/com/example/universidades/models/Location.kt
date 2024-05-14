package com.example.universidades.models

import com.google.gson.annotations.SerializedName

data class Location (
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
)
