package com.pucetec.timeformed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pucetec.timeformed.model.Med
import com.pucetec.timeformed.model.Treatment
import com.pucetec.timeformed.model.Take
import com.pucetec.timeformed.repository.MedRepository
import com.pucetec.timeformed.repository.TreatmentRepository
import com.pucetec.timeformed.repository.TakeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val medRepository: MedRepository = MedRepository(),
    private val treatmentRepository: TreatmentRepository = TreatmentRepository(),
    private val takeRepository: TakeRepository = TakeRepository()
) : ViewModel() {

    // ✅ Flujos que la UI observa en HomeScreen
    private val _meds = MutableStateFlow<List<Med>>(emptyList())
    val meds: StateFlow<List<Med>> get() = _meds

    private val _treatments = MutableStateFlow<List<Treatment>>(emptyList())
    val treatments: StateFlow<List<Treatment>> get() = _treatments

    private val _takes = MutableStateFlow<List<Take>>(emptyList())
    val takes: StateFlow<List<Take>> get() = _takes

    // ✅ Estado para mostrar si está cargando (por si luego quieres un Loading Spinner en Home)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // ✅ Estado de error básico (puede usarse para Snackbars)
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    /**
     * 🚀 Carga todos los datos del backend:
     * - Medicamentos
     * - Tratamientos
     * - Tomas
     *
     * Se llama desde HomeScreen en LaunchedEffect.
     */
    fun loadHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                // 📡 Llamadas al backend (Retrofit → Spring Boot)
                val medsList = medRepository.getMeds()
                val treatmentsList = treatmentRepository.getTreatments()
                val takesList = takeRepository.getTakes()

                // ✅ Actualizamos los StateFlow
                _meds.value = medsList
                _treatments.value = treatmentsList
                _takes.value = takesList

            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "❌ Error al cargar datos. Verifica tu conexión."
            } finally {
                _isLoading.value = false
            }
        }
    }
}
