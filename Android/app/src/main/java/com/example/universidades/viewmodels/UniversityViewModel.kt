package com.example.universidades.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.universidades.api.ApiService
import com.example.universidades.models.Category
import com.example.universidades.models.Location
import com.example.universidades.models.University
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UniversityViewModel(private val apiService: ApiService) : ViewModel() {

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

    fun loadUniversities() {
        viewModelScope.launch {
            try {
                _universityUiState.value = UniversityUiState.Loading
                val universities = apiService.getAllUniversities()
                _universityUiState.value = UniversityUiState.Success(universities)
            } catch (e: Exception) {
                Log.e("UniversityViewModel", "Error cargando universidades: ${e.message}", e)
                _universityUiState.value = UniversityUiState.Error("Error cargando universidades: ${e.message}")
            }
        }
    }

    fun loadLocations() {
        viewModelScope.launch {
            try {
                val locations = apiService.getAllLocations()
                _locations.postValue(locations)
            } catch (e: Exception) {
                Log.e("UniversityViewModel", "Error cargando ubicaciones: ${e.message}", e)
            }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = apiService.getAllCategories()
                _categories.postValue(categories)
            } catch (e: Exception) {
                Log.e("UniversityViewModel", "Error cargando categorías: ${e.message}", e)
            }
        }
    }

    fun createUniversity(university: University) {
        viewModelScope.launch {
            try {
                apiService.createUniversity(university)
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
                apiService.deleteUniversity(universityId)
                loadUniversities()
            } catch (e: Exception) {
                _universityUiState.value = UniversityUiState.Error("Error eliminando universidad: ${e.message}")
            }
        }
    }

    fun searchUniversities(id: String, category: String, location: String) {
        val allUniversities = (universityUiState.value as? UniversityUiState.Success)?.universities ?: emptyList()
        val filteredUniversities = allUniversities.filter { university ->
            (id.isEmpty() || university.id.toString() == id) &&
                    (category.isEmpty() || university.category.name.equals(category, ignoreCase = true)) &&
                    (location.isEmpty() || university.location.name.equals(location, ignoreCase = true))
        }
        _universityUiState.value = UniversityUiState.Success(filteredUniversities)
    }


    fun getLocationByName(name: String): Location? {
        val locations: List<Location> = _locations.value ?: return null
        return locations.find { it.name == name }
    }

    fun getCategoryByName(name: String): Category? {
        val categories: List<Category> = _categories.value ?: return null
        return categories.find { it.name == name }
    }

}

sealed class UniversityUiState {
    data object Loading : UniversityUiState()
    data class Success(val universities: List<University>) : UniversityUiState()
    data class Error(val message: String) : UniversityUiState()
}
