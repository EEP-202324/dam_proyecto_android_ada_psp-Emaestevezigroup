package com.example.universidades.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.universidades.api.UniversityApi
import com.example.universidades.models.University
import kotlinx.coroutines.launch

class UniversityViewModel : ViewModel() {
    private val _universityUiState = MutableLiveData<UniversityUiState>()
    val universityUiState: LiveData<UniversityUiState> = _universityUiState

    init {
        Log.d("UniversityViewModel", "Before init***")
        loadUniversities()
        Log.d("UniversityViewModel", "After init***")
    }

    private fun loadUniversities() {
        viewModelScope.launch {
            try {
                // Show a loading state while fetching data
                _universityUiState.value = UniversityUiState.Loading
                Log.d("UniversityViewModel", "Fetching universities data from API...")

                // Try to fetch universities from the API
                val universities = UniversityApi.retrofitService.getAllUniversities()

                // Log the fetched universities
                Log.d("UniversityViewModel", "Universities fetched: $universities")

                // Update the state with the loaded list of universities
                _universityUiState.value = UniversityUiState.Success(universities)
            } catch (e: Exception) {
                // Handle any errors that occur during data loading
                Log.e("UniversityViewModel", "Error loading universities: ${e.message}", e)
                _universityUiState.value = UniversityUiState.Error("Error loading universities: ${e.message}")
            }
        }
    }

    fun createUniversity(university: University) {
        viewModelScope.launch {
            try {
                val createdUniversity = UniversityApi.retrofitService.createUniversity(university)
                Log.d("UniversityViewModel", "University created: $createdUniversity")
                // Aquí puedes agregar la lógica necesaria para manejar el resultado de la creación de la universidad
            } catch (e: Exception) {
                Log.e("UniversityViewModel", "Error creating university: ${e.message}", e)
                // Aquí puedes agregar la lógica necesaria para manejar errores en la creación de la universidad
            }
        }
    }

}

sealed class UniversityUiState {
    object Loading : UniversityUiState()
    data class Success(val universities: List<University>) : UniversityUiState()
    data class Error(val message: String) : UniversityUiState()
}
