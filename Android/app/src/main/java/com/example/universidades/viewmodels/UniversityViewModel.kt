package com.example.universidades.viewmodels

import androidx.lifecycle.ViewModel
import com.example.universidades.api.Api
import com.example.universidades.models.University
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UniversityViewModel(private val api: Api) : ViewModel() {

    val universities: Flow<List<University>> = flow {
        emit(api.getAllUniversities())
    }

    suspend fun getUniversityById(id: Long): University {
        return api.getUniversityById(id)
    }

    suspend fun createUniversity(university: University): University {
        return api.createUniversity(university)
    }

    suspend fun updateUniversity(id: Long, university: University): University {
        return api.updateUniversity(id, university)
    }

    suspend fun deleteUniversity(id: Long) {
        api.deleteUniversity(id)
    }
}
