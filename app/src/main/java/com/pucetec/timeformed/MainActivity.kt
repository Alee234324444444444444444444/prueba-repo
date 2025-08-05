package com.pucetec.timeformed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.pucetec.timeformed.navigation.NavGraph
import com.pucetec.timeformed.ui.components.BottomBar
import com.pucetec.timeformed.ui.theme.TimeForMedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // ✅ Usamos el theme correcto
            TimeForMedTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { BottomBar(navController) } // ✅ Barra de navegación abajo
                ) { innerPadding ->
                    NavGraph(navController = navController)
                }
            }
        }
    }
}
