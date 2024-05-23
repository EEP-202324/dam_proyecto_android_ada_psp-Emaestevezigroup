package com.example.universidades.api

import android.util.Log
import com.example.universidades.models.Category
import com.example.universidades.models.Location
import com.example.universidades.models.University
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.Response
import retrofit2.http.GET


private const val BASE_URL = "http://192.168.144.14:8080/"

val gson: Gson = GsonBuilder().create()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()


interface ApiService {
    @GET("/universities")
    suspend fun getAllUniversities(): List<University>

    @GET("/locations")
    suspend fun getAllLocations(): List<Location>

    @GET("/categories")
    suspend fun getAllCategories(): List<Category>

    @POST("/universities")
    suspend fun createUniversity(@Body university: University): University

    @DELETE("/universities/{id}")
    suspend fun deleteUniversity(@Path("id") id: Long): Response<Unit>

    @PUT("/universities/{id}")
    suspend fun updateUniversity(@Path("id") id: Long, @Body university: University): University

}



object UniversityApi {
    val retrofitService: ApiService by lazy {
        Log.d("ApiService", "retrofit de UniversityApi")
        retrofit.create(ApiService::class.java)
    }
}