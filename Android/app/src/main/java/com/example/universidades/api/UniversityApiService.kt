package com.example.universidades.api

import android.util.Log
import com.example.universidades.models.University
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.Response


private const val BASE_URL = "http://192.168.144.14:8080/"

val gson: Gson = GsonBuilder().create()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()



interface ApiService {
    @GET("/universities")
    suspend fun getAllUniversities(): List<University>

    @GET("/universities/{id}")
    suspend fun getUniversityById(@Path("id") id: Long): University

    @GET("/universities/byLocation/{id}")
    suspend fun getUniversitiesByLocation(@Path("id") id: Long): List<University>

    @GET("/universities/byCategory/{id}")
    suspend fun getUniversitiesByCategory(@Path("id") id: Long): List<University>

    @POST("/universities")
    suspend fun createUniversity(@Body university: University): University

    @PUT("/universities/{id}")
    suspend fun updateUniversity(@Path("id") id: Long, @Body universityDetails: University): University

    @DELETE("/universities/{id}")
    suspend fun deleteUniversity(@Path("id") id: Long): Response<Unit>
}


object UniversityApi {
    val retrofitService: ApiService by lazy {
        Log.d("ApiService", "retrofit de UniversityApi")
        retrofit.create(ApiService::class.java)
    }
}