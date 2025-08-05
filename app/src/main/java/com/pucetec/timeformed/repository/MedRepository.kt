package com.pucetec.timeformed.repository

import com.pucetec.timeformed.model.Med
import com.pucetec.timeformed.model.dto.MedRequest
import com.pucetec.timeformed.network.ApiService

class MedRepository(private val apiService: ApiService) {

    // Obtener todos los medicamentos
    suspend fun getMeds(): List<Med> {
        return apiService.getMeds()
    }

    // Crear un nuevo medicamento
    suspend fun createMed(request: MedRequest): Med {
        return apiService.createMed(request)
    }

    // Obtener un medicamento por ID
    suspend fun getMed(id: Long): Med {
        return apiService.getMed(id)
    }

    // Actualizar un medicamento existente
    suspend fun updateMed(id: Long, request: MedRequest): Med {
        return apiService.updateMed(id, request)
    }

    // Eliminar un medicamento por ID
    suspend fun deleteMed(id: Long) {
        apiService.deleteMed(id)
    }
}
