package com.pucetec.timeformed.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pucetec.timeformed.model.Med
import com.pucetec.timeformed.model.dto.MedRequest
import com.pucetec.timeformed.repository.MedRepository
import kotlinx.coroutines.launch

class MedViewModel(private val repository: MedRepository) : ViewModel() {

    // Lista de medicamentos
    var meds by mutableStateOf<List<Med>>(emptyList())
        private set

    // Estado para el nombre y descripción (formulario)
    var name by mutableStateOf("")
    var description by mutableStateOf("")

    // Estados de carga y error
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var successMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadMeds()
    }

    // 🔄 Cargar medicamentos desde la API
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

    // ✅ Guardar nuevo medicamento
    fun saveMed() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            successMessage = null

            try {
                val request = MedRequest(name = name.trim(), description = description.trim())

                if (request.name.isBlank() || request.description.isBlank()) {
                    errorMessage = "Los campos no pueden estar vacíos."
                    isLoading = false
                    return@launch
                }

                repository.createMed(request)
                successMessage = "Medicamento guardado correctamente."

                // Limpiar formulario
                name = ""
                description = ""

                // Recargar lista
                loadMeds()

            } catch (e: Exception) {
                errorMessage = "Error al guardar: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }
}
