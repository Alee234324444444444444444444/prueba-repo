package com.pucetec.timeformed.repository

import com.pucetec.timeformed.model.Take
import com.pucetec.timeformed.network.RetrofitClient

class TakeRepository {
    private val api = RetrofitClient.apiService

    suspend fun getTakes(): List<Take> = api.getTakes()
    suspend fun getTake(id: Long): Take = api.getTake(id)
    suspend fun createTake(take: Take): Take = api.createTake(take)
    suspend fun updateTake(id: Long, take: Take): Take = api.updateTake(id, take)
    suspend fun deleteTake(id: Long) = api.deleteTake(id)
}
