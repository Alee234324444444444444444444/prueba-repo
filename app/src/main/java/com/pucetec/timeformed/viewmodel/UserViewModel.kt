package com.pucetec.timeformed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pucetec.timeformed.model.User
import com.pucetec.timeformed.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository = UserRepository()   // ✅ Usa tu repo
) : ViewModel() {

    // ✅ Lista de usuarios
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    // ✅ Usuario activo (para login)
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    // 📥 Cargar todos los usuarios
    fun loadUsers() {
        viewModelScope.launch {
            try {
                _users.value = repository.getUsers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // ➕ Registrar usuario
    fun registerUser(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val newUser = repository.createUser(user)
                _currentUser.value = newUser
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onError("No se pudo registrar al usuario.")
            }
        }
    }

    // 🔐 Login básico por email y nombre
    fun login(email: String, name: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val allUsers = repository.getUsers()
                val foundUser = allUsers.find { it.email == email && it.name == name }

                if (foundUser != null) {
                    _currentUser.value = foundUser
                    onSuccess()
                } else {
                    onError("Usuario no encontrado. Revisa tu nombre o email.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onError("Error al intentar iniciar sesión.")
            }
        }
    }

    // ✏️ Actualizar perfil
    fun updateUser(id: Long, user: User) {
        viewModelScope.launch {
            try {
                repository.updateUser(id, user)
                loadUsers() // 🔄 refresca la lista
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 🚪 Cerrar sesión
    fun logout() {
        _currentUser.value = null
    }
}
