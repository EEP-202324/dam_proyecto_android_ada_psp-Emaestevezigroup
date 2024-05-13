package com.example.universidades.api

import com.example.universidades.models.University
import retrofit2.http.*

interface Api {
    @GET("/universities")
    suspend fun getAllUniversities(): List<University>

    @GET("/universities/{id}")
    suspend fun getUniversityById(@Path("id") id: Long): University

    @POST("/universities")
    suspend fun createUniversity(@Body university: University): University

    @PUT("/universities/{id}")
    suspend fun updateUniversity(@Path("id") id: Long, @Body university: University): University

    @DELETE("/universities/{id}")
    suspend fun deleteUniversity(@Path("id") id: Long)
}
