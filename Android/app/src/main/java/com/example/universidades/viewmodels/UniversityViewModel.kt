package com.example.universidades.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.universidades.api.UniversityApi
import com.example.universidades.models.University
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UniversityViewModel : ViewModel() {
    private val _universityUiState = MutableLiveData<UniversityUiState>()
    val universityUiState: LiveData<UniversityUiState> = _universityUiState

    private val _errorMessage = MutableLiveData<String>()

    init {
        loadUniversities()
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
