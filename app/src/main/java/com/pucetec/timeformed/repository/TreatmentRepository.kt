package com.pucetec.timeformed.repository

import com.pucetec.timeformed.model.Treatment
import com.pucetec.timeformed.network.RetrofitClient

class TreatmentRepository {
    private val api = RetrofitClient.apiService

    suspend fun getTreatments(): List<Treatment> = api.getTreatments()
    suspend fun getTreatment(id: Long): Treatment = api.getTreatment(id)
    suspend fun createTreatment(treatment: Treatment): Treatment = api.createTreatment(treatment)
    suspend fun updateTreatment(id: Long, treatment: Treatment): Treatment = api.updateTreatment(id, treatment)
    suspend fun deleteTreatment(id: Long) = api.deleteTreatment(id)
}
