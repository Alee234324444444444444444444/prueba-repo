package com.pucetec.timeformed.ui.takes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pucetec.timeformed.viewmodel.TakeViewModel
import com.pucetec.timeformed.model.Take

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakesScreen(
    navController: NavHostController,          // ✅ AHORA recibe navController
    viewModel: TakeViewModel = viewModel()
) {
    val takes by viewModel.takes.collectAsState()

    // 🚀 Cargamos datos al entrar
    LaunchedEffect(Unit) { viewModel.loadTakes() }

    Scaffold { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("⏰ Historial de Tomas", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(12.dp))

            // ✅ Si no hay datos, mostramos un mensaje
            if (takes.isEmpty()) {
                Text("No hay tomas registradas todavía.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else {
                LazyColumn {
                    items(takes) { take ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text("Programada: ${take.scheduledDateTime}")
                                Text("¿Tomada? ${if (take.wasTaken) "✅ Sí" else "❌ No"}")

                                Spacer(Modifier.height(8.dp))

                                Button(
                                    onClick = {
                                        // ✅ Solo permitir marcar como tomada si NO está tomada ya
                                        if (!take.wasTaken) {
                                            viewModel.updateTake(
                                                take.id!!,
                                                take.copy(
                                                    wasTaken = true,
                                                    takenDateTime = "2025-08-03T12:00:00" // ⚠️ ejemplo: luego pones hora real
                                                )
                                            )
                                        }
                                    },
                                    enabled = !take.wasTaken, // 🔒 Deshabilitar si ya está tomada
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (take.wasTaken)
                                            MaterialTheme.colorScheme.secondary
                                        else
                                            MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Text(if (take.wasTaken) "Tomada" else "Marcar como tomada")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
