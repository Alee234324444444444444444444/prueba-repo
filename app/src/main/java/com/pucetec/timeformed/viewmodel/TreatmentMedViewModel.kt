package com.pucetec.timeformed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pucetec.timeformed.model.TreatmentMed
import com.pucetec.timeformed.repository.TreatmentMedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TreatmentMedViewModel : ViewModel() {
    private val repository = TreatmentMedRepository()

    private val _treatmentMeds = MutableStateFlow<List<TreatmentMed>>(emptyList())
    val treatmentMeds: StateFlow<List<TreatmentMed>> = _treatmentMeds

    fun loadTreatmentMeds() {
        viewModelScope.launch {
            _treatmentMeds.value = repository.getTreatmentMeds()
        }
    }

    fun addTreatmentMed(tm: TreatmentMed) {
        viewModelScope.launch {
            repository.createTreatmentMed(tm)
            loadTreatmentMeds()
        }
    }

    fun updateTreatmentMed(id: Long, tm: TreatmentMed) {
        viewModelScope.launch {
            repository.updateTreatmentMed(id, tm)
            loadTreatmentMeds()
        }
    }

    fun deleteTreatmentMed(id: Long) {
        viewModelScope.launch {
            repository.deleteTreatmentMed(id)
            loadTreatmentMeds()
        }
    }
}
