package com.pucetec.timeformed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pucetec.timeformed.model.Take
import com.pucetec.timeformed.repository.TakeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TakeViewModel : ViewModel() {
    private val repository = TakeRepository()

    private val _takes = MutableStateFlow<List<Take>>(emptyList())
    val takes: StateFlow<List<Take>> = _takes

    fun loadTakes() {
        viewModelScope.launch {
            _takes.value = repository.getTakes()
        }
    }

    fun addTake(take: Take) {
        viewModelScope.launch {
            repository.createTake(take)
            loadTakes()
        }
    }

    fun updateTake(id: Long, take: Take) {
        viewModelScope.launch {
            repository.updateTake(id, take)
            loadTakes()
        }
    }

    fun deleteTake(id: Long) {
        viewModelScope.launch {
            repository.deleteTake(id)
            loadTakes()
        }
    }
}
