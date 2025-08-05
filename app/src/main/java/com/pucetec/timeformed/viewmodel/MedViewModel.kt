package com.pucetec.timeformed.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pucetec.timeformed.model.Med
import com.pucetec.timeformed.model.dto.MedRequest
import com.pucetec.timeformed.repository.MedRepository
import kotlinx.coroutines.launch

class MedViewModel : ViewModel() {

    private val repository = MedRepository()

    var meds by mutableStateOf<List<Med>>(emptyList())
        private set

    var name by mutableStateOf("")
    var description by mutableStateOf("")

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var successMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadMeds()
    }

    fun loadMeds() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                meds = repository.getMeds()
            } catch (e: Exception) {
                errorMessage = "Error al cargar medicamentos: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    fun saveMed() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            successMessage = null
            try {
                val request = MedRequest(name.trim(), description.trim())
                if (request.name.isBlank() || request.description.isBlank()) {
                    errorMessage = "Los campos no pueden estar vacíos."
                    isLoading = false
                    return@launch
                }
                repository.createMed(request)
                successMessage = "Medicamento guardado correctamente."
                name = ""
                description = ""
                loadMeds()
            } catch (e: Exception) {
                errorMessage = "Error al guardar: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }
}
