package com.pucetec.timeformed.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pucetec.timeformed.viewmodel.HomeViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = viewModel()
) {
    // ✅ Observa los datos del ViewModel
    val meds by homeViewModel.meds.collectAsState()
    val treatments by homeViewModel.treatments.collectAsState()
    val takes by homeViewModel.takes.collectAsState()

    // 🚀 Cargar datos del backend al abrir Home
    LaunchedEffect(Unit) {
        homeViewModel.loadHomeData()
    }

    // 📆 Fecha actual formateada
    val today = LocalDate.now()
    val dayName = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
    val dateText = "$dayName, ${today.dayOfMonth} de ${today.month.getDisplayName(TextStyle.FULL, Locale("es", "ES"))}"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(16.dp)
    ) {

        // 🔹 Saludo con fondo degradado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(Color(0xFF6A5AE0), Color(0xFF8A7FFB))
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(20.dp)
        ) {
            Column {
                Text("¡Hola! 👋", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(dateText, color = Color.White, fontSize = 16.sp)
            }
        }

        Spacer(Modifier.height(16.dp))

        // 🔹 Tarjetas de resumen (Medicamentos y Tratamientos)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SummaryCard(
                title = "Medicamentos",
                count = meds.size.toString(),
                color = Color(0xFF4CC9F0),
                modifier = Modifier.weight(1f)
            )
            SummaryCard(
                title = "Tratamientos",
                count = treatments.size.toString(),
                color = Color(0xFFBDE0FE),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(16.dp))

        // 🔹 Próxima toma
        val nextTake = takes.find { !it.wasTaken }
        if (nextTake != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(listOf(Color(0xFFFF9A76), Color(0xFFFFC56D))),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Column {
                    Text("Próxima toma", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)

                    // ✅ Hora formateada
                    val formattedTime = nextTake.scheduledDateTime.substring(11, 16) // HH:mm
                    Text(formattedTime, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)

                    // ⚠️ Por ahora muestra el ID del medicamento (se puede mejorar luego)
                    Text("Medicamento ID: ${nextTake.treatmentMedId}", color = Color.White, fontSize = 16.sp)
                }
            }
        } else {
            Text("✅ No tienes tomas pendientes por ahora", color = Color.Gray, fontSize = 14.sp)
        }

        Spacer(Modifier.height(16.dp))

        // 🔹 Lista de próximas tomas
        Text("Próximas tomas", style = MaterialTheme.typography.titleMedium)

        if (takes.isEmpty()) {
            Text("No hay tomas programadas.", color = Color.Gray)
        } else {
            LazyColumn {
                items(takes.take(5)) { take ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            val hora = take.scheduledDateTime.substring(11, 16)
                            Text("ID Tratamiento: ${take.treatmentMedId}", fontWeight = FontWeight.Bold)
                            Text("$hora • ${if (take.wasTaken) "✅ Tomada" else "⏳ Pendiente"}", color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryCard(title: String, count: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(count, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(title, color = Color.White)
        }
    }
}
