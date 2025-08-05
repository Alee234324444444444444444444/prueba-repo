package com.pucetec.timeformed.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pucetec.timeformed.viewmodel.UserViewModel
import com.pucetec.timeformed.model.User



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    userViewModel: UserViewModel = viewModel()
) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var age by remember { mutableStateOf(TextFieldValue("")) }
    var isRegisterMode by remember { mutableStateOf(false) }  // ✅ alterna login / registro
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            if (isRegisterMode) "Crear cuenta" else "Iniciar sesión",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        // 🔹 Si estamos en modo REGISTRO pedimos edad
        if (isRegisterMode) {
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔘 Botón principal
        Button(
            onClick = {
                if (isRegisterMode) {
                    // ✅ Registro
                    if (name.text.isNotBlank() && email.text.isNotBlank() && age.text.isNotBlank()) {
                        userViewModel.registerUser(
                            User(
                                name = name.text,
                                email = email.text,
                                age = age.text.toInt()
                            ),
                            onSuccess = {
                                navController.navigate("home") { popUpTo("login") { inclusive = true } }
                            },
                            onError = { error -> errorMessage = error }
                        )
                    } else {
                        errorMessage = "Todos los campos son obligatorios."
                    }
                } else {
                    // ✅ Login
                    userViewModel.login(
                        email.text,
                        name.text,
                        onSuccess = {
                            navController.navigate("home") { popUpTo("login") { inclusive = true } }
                        },
                        onError = { error -> errorMessage = error }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isRegisterMode) "Registrar" else "Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🔄 Cambiar entre login y registro
        TextButton(onClick = { isRegisterMode = !isRegisterMode }) {
            Text(if (isRegisterMode) "¿Ya tienes cuenta? Inicia sesión" else "¿No tienes cuenta? Regístrate")
        }

        // ❌ Mensaje de error
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}
