package com.pucetec.timeformed.ui.treatmentmeds

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pucetec.timeformed.viewmodel.TreatmentMedViewModel
import com.pucetec.timeformed.viewmodel.MedViewModel
import com.pucetec.timeformed.viewmodel.TreatmentViewModel
import com.pucetec.timeformed.model.TreatmentMed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreatmentMedsScreen(
    navController: NavHostController,
    treatmentMedViewModel: TreatmentMedViewModel = viewModel(),
    medViewModel: MedViewModel = viewModel(),
    treatmentViewModel: TreatmentViewModel = viewModel()
) {
    val treatmentMeds by treatmentMedViewModel.treatmentMeds.collectAsState()
    val meds = medViewModel.meds
    val treatments by treatmentViewModel.treatments.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedTreatment by remember { mutableStateOf<Long?>(null) }
    var selectedMed by remember { mutableStateOf<Long?>(null) }
    var dose by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var startHour by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        treatmentMedViewModel.loadTreatmentMeds()
        medViewModel.loadMeds()
        treatmentViewModel.loadTreatments()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("📦 Asignar medicamentos a tratamientos", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(12.dp))

            if (treatmentMeds.isEmpty()) {
                Text("No hay medicamentos asignados todavía.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else {
                LazyColumn {
                    items(treatmentMeds) { tm ->
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text("Tratamiento: ${treatments.find { it.id == tm.treatmentId }?.name ?: tm.treatmentId}")
                                Text("Medicamento: ${meds.find { it.id == tm.medId }?.name ?: tm.medId}")
                                Text("${tm.dose} - cada ${tm.frequencyHours}h - ${tm.durationDays} días")

                                Spacer(Modifier.height(8.dp))

                                Button(
                                    onClick = { tm.id?.let { treatmentMedViewModel.deleteTreatmentMed(it) } },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Text("Eliminar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = {
                    if (selectedTreatment != null && selectedMed != null &&
                        dose.isNotBlank() && frequency.isNotBlank() &&
                        duration.isNotBlank() && startHour.isNotBlank()
                    ) {
                        treatmentMedViewModel.addTreatmentMed(
                            TreatmentMed(
                                treatmentId = selectedTreatment!!,
                                medId = selectedMed!!,
                                dose = dose,
                                frequencyHours = frequency.toInt(),
                                durationDays = duration.toInt(),
                                startHour = startHour
                            )
                        )

                        selectedTreatment = null
                        selectedMed = null
                        dose = ""
                        frequency = ""
                        duration = ""
                        startHour = ""
                        showDialog = false
                    }
                }) { Text("Guardar") }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false }) { Text("Cancelar") }
            },
            title = { Text("Asignar medicamento") },
            text = {
                Column {
                    Text("Tratamiento")
                    DropdownMenuBox(
                        options = treatments.map { it.id to it.name },
                        onItemSelected = { id -> selectedTreatment = id }
                    )

                    Text("Medicamento")
                    DropdownMenuBox(
                        options = meds.map { it.id to it.name },
                        onItemSelected = { id -> selectedMed = id }
                    )

                    OutlinedTextField(value = dose, onValueChange = { dose = it }, label = { Text("Dosis (ej. 500mg)") })
                    OutlinedTextField(value = frequency, onValueChange = { frequency = it }, label = { Text("Frecuencia (h)") })
                    OutlinedTextField(value = duration, onValueChange = { duration = it }, label = { Text("Duración (días)") })
                    OutlinedTextField(value = startHour, onValueChange = { startHour = it }, label = { Text("Hora inicio (HH:mm)") })
                }
            }
        )
    }
}

@Composable
fun DropdownMenuBox(
    options: List<Pair<Long?, String>>,
    onItemSelected: (Long?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Selecciona...") }

    Box {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            readOnly = true,
            label = { Text("Selecciona") },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { (id, name) ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = {
                        selectedText = name
                        expanded = false
                        onItemSelected(id)
                    }
                )
            }
        }
    }
}
