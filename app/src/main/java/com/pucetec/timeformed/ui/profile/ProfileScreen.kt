package com.pucetec.timeformed.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pucetec.timeformed.viewmodel.UserViewModel
import com.pucetec.timeformed.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: UserViewModel = viewModel()
) {
    // ✅ Ahora el tipo está bien especificado
    val users: List<User> by viewModel.users.collectAsState(initial = emptyList())

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    // 🚀 Cargar datos del usuario
    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("👤 Perfil", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        if (users.isNotEmpty()) {
            val user = users.first()  // ⚠️ Por ahora asumimos 1 solo usuario

            // ✅ Inicializamos valores
            name = user.name
            email = user.email
            age = user.age.toString()

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    viewModel.updateUser(
                        user.id!!,
                        User(
                            id = user.id,
                            name = name,
                            email = email,
                            age = age.toInt()
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar perfil")
            }
        } else {
            Text("No hay usuario registrado.")
        }
    }
}
