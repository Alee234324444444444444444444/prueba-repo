package com.pucetec.timeformed.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pucetec.timeformed.ui.auth.LoginScreen
import com.pucetec.timeformed.ui.home.HomeScreen
import com.pucetec.timeformed.ui.meds.MedsScreen
import com.pucetec.timeformed.ui.treatments.TreatmentsScreen
import com.pucetec.timeformed.ui.treatmentmeds.TreatmentMedsScreen
import com.pucetec.timeformed.ui.takes.TakesScreen
import com.pucetec.timeformed.ui.profile.ProfileScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "login") {

        // 🔵 Nueva pantalla de login
        composable("login") { LoginScreen(navController) }

        composable("home") { HomeScreen(navController) }
        composable("meds") { MedsScreen() }

        composable("treatments") { TreatmentsScreen(navController) }
        composable("treatmentmeds") { TreatmentMedsScreen(navController) }
        composable("takes") { TakesScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
    }
}


