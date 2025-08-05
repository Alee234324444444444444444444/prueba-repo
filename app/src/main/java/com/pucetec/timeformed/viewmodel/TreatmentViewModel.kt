package com.pucetec.timeformed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pucetec.timeformed.model.Treatment
import com.pucetec.timeformed.repository.TreatmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TreatmentViewModel : ViewModel() {
    private val repository = TreatmentRepository()

    private val _treatments = MutableStateFlow<List<Treatment>>(emptyList())
    val treatments: StateFlow<List<Treatment>> = _treatments

    fun loadTreatments() {
        viewModelScope.launch {
            _treatments.value = repository.getTreatments()
        }
    }

    fun addTreatment(treatment: Treatment) {
        viewModelScope.launch {
            repository.createTreatment(treatment)
            loadTreatments()
        }
    }

    fun updateTreatment(id: Long, treatment: Treatment) {
        viewModelScope.launch {
            repository.updateTreatment(id, treatment)
            loadTreatments()
        }
    }

    fun deleteTreatment(id: Long) {
        viewModelScope.launch {
            repository.deleteTreatment(id)
            loadTreatments()
        }
    }
}
