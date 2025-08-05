package com.pucetec.timeformed.repository

import com.pucetec.timeformed.model.User
import com.pucetec.timeformed.network.RetrofitClient

class UserRepository {
    private val api = RetrofitClient.apiService

    suspend fun getUsers(): List<User> = api.getUsers()
    suspend fun createUser(user: User): User = api.createUser(user)
    suspend fun getUser(id: Long): User = api.getUser(id)
    suspend fun updateUser(id: Long, user: User): User = api.updateUser(id, user)
    suspend fun deleteUser(id: Long) = api.deleteUser(id)
}
