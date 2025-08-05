package com.pucetec.timeformed.ui.treatments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pucetec.timeformed.viewmodel.TreatmentViewModel
import com.pucetec.timeformed.model.Treatment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreatmentsScreen(
    navController: NavHostController,                // ✅ ahora recibe navController
    viewModel: TreatmentViewModel = viewModel()
) {
    val treatments by viewModel.treatments.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // 🚀 Cargar lista de tratamientos al entrar
    LaunchedEffect(Unit) { viewModel.loadTreatments() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("🩺 Tratamientos", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(12.dp))

            if (treatments.isEmpty()) {
                Text("No hay tratamientos registrados.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else {
                LazyColumn {
                    items(treatments) { treatment ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(treatment.name, style = MaterialTheme.typography.titleMedium)
                                Text(treatment.description, style = MaterialTheme.typography.bodyMedium)

                                Spacer(Modifier.height(8.dp))

                                Row {
                                    Button(
                                        onClick = { treatment.id?.let { viewModel.deleteTreatment(it) } },
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                    ) {
                                        Text("Eliminar")
                                    }
                                    Spacer(Modifier.width(8.dp))
                                    OutlinedButton(
                                        onClick = {
                                            // 🚀 Aquí puedes navegar a la pantalla de TreatmentMeds para asignar medicamentos
                                            navController.navigate("treatmentmeds")
                                        }
                                    ) {
                                        Text("Asignar medicamento")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // 🔵 Diálogo para añadir tratamiento
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = {
                    if (name.isNotBlank() && description.isNotBlank()) {
                        // ⚠️ TEMPORAL: userId fijo en 1. Luego, usar el del usuario logueado
                        viewModel.addTreatment(Treatment(name = name, description = description, userId = 1))
                        // ✅ Limpiamos los campos
                        name = ""
                        description = ""
                        showDialog = false
                    }
                }) { Text("Guardar") }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false }) { Text("Cancelar") }
            },
            title = { Text("Nuevo Tratamiento") },
            text = {
                Column {
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
                    OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })
                }
            }
        )
    }
}
