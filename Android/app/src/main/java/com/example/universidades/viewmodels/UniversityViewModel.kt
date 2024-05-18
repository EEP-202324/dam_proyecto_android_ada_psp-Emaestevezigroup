package com.example.universidades.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.universidades.api.UniversityApi
import com.example.universidades.models.Category
import com.example.universidades.models.Location
import com.example.universidades.models.University
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UniversityViewModel : ViewModel() {
    private val _universityUiState = MutableLiveData<UniversityUiState>()
    val universityUiState: LiveData<UniversityUiState> = _universityUiState

    private val _errorMessage = MutableLiveData<String>()

    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> = _locations

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    init {
        loadUniversities()
        loadLocations()
        loadCategories()
    }

    private fun loadUniversities() {
        viewModelScope.launch {
            try {
                _universityUiState.value = UniversityUiState.Loading
                val universities = UniversityApi.retrofitService.getAllUniversities()
                _universityUiState.value = UniversityUiState.Success(universities)
            } catch (e: Exception) {
                Log.e("UniversityViewModel", "Error cargando universidades: ${e.message}", e)
                _universityUiState.value = UniversityUiState.Error("Error cargando universidades: ${e.message}")
            }
        }
    }

    private fun loadLocations() {
        viewModelScope.launch {
            try {
                val locations = UniversityApi.retrofitService.getAllLocations()
                _locations.postValue(locations)
            } catch (e: Exception) {
                Log.e("UniversityViewModel", "Error cargando ubicaciones: ${e.message}", e)
                // Manejar el error según sea necesario
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = UniversityApi.retrofitService.getAllCategories()
                _categories.postValue(categories)
            } catch (e: Exception) {
                Log.e("UniversityViewModel", "Error cargando categorías: ${e.message}", e)
                // Manejar el error según sea necesario
            }
        }
    }

    fun createUniversity(university: University) {
        viewModelScope.launch {
            try {
                UniversityApi.retrofitService.createUniversity(university)
                loadUniversities()
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is HttpException -> {
                        when (e.code()) {
                            400 -> "El número de teléfono debe tener 9 dígitos o el email debe tener un formato válido."
                            else -> "Error creando universidad: ${e.message}"
                        }
                    }
                    else -> "Error creando universidad: ${e.message}"
                }
                _errorMessage.postValue(errorMessage)
                Log.e("UniversityViewModel", errorMessage, e)
            }
        }
    }

    fun deleteUniversity(universityId: Long) {
        viewModelScope.launch {
            try {
                UniversityApi.retrofitService.deleteUniversity(universityId)
                loadUniversities()
            } catch (e: Exception) {
                _universityUiState.value = UniversityUiState.Error("Error deleting university: ${e.message}")
            }
        }
    }
}

sealed class UniversityUiState {
    object Loading : UniversityUiState()
    data class Success(val universities: List<University>) : UniversityUiState()
    data class Error(val message: String) : UniversityUiState()
}
