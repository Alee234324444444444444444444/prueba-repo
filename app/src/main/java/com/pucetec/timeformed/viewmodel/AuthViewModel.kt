package com.pucetec.timeformed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pucetec.timeformed.model.User
import com.pucetec.timeformed.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    var loggedUser: User? = null
        private set

    fun login(email: String, name: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val users = userRepository.getUsers()
                val matchedUser = users.find { it.email == email && it.name == name }

                if (matchedUser != null) {
                    loggedUser = matchedUser
                    onSuccess()
                } else {
                    onError("Usuario no encontrado.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onError("Error de conexión.")
            }
        }
    }

    fun registerUser(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val newUser = userRepository.createUser(user)
                loggedUser = newUser
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onError("Error al registrar usuario.")
            }
        }
    }
}
