package com.pucetec.timeformed.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

data class BottomNavItem(val label: String, val icon: @Composable () -> Unit, val route: String)

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Inicio", { Icon(Icons.Default.Home, contentDescription = null) }, "home"),
        BottomNavItem("Medicamentos", { Icon(Icons.Default.MedicalServices, contentDescription = null) }, "meds"),
        BottomNavItem("Tratamientos", { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) }, "treatments"),
        BottomNavItem("Historial", { Icon(Icons.Default.History, contentDescription = null) }, "takes"),
        BottomNavItem("Perfil", { Icon(Icons.Default.Person, contentDescription = null) }, "profile")
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = false, // ⚠️ Luego se puede mejorar con un estado de selección
                onClick = { navController.navigate(item.route) },
                icon = item.icon,
                label = { Text(item.label) }
            )
        }
    }
}
