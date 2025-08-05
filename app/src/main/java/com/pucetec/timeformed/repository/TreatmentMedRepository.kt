package com.pucetec.timeformed.repository

import com.pucetec.timeformed.model.TreatmentMed
import com.pucetec.timeformed.network.RetrofitClient

class TreatmentMedRepository {
    private val api = RetrofitClient.apiService

    suspend fun getTreatmentMeds(): List<TreatmentMed> = api.getTreatmentMeds()
    suspend fun getTreatmentMed(id: Long): TreatmentMed = api.getTreatmentMed(id)
    suspend fun createTreatmentMed(tm: TreatmentMed): TreatmentMed = api.createTreatmentMed(tm)
    suspend fun updateTreatmentMed(id: Long, tm: TreatmentMed): TreatmentMed = api.updateTreatmentMed(id, tm)
    suspend fun deleteTreatmentMed(id: Long) = api.deleteTreatmentMed(id)
}
