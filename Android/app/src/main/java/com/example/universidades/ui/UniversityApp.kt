package com.example.universidades.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.universidades.ui.Screen.AddUniversityScreen
import com.example.universidades.ui.Screen.StartScreen
import com.example.universidades.ui.Screen.UniversityDetailScreen
import com.example.universidades.ui.Screen.UniversityListScreen

@Composable
fun UniversityApp(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "start") {
        composable(route = "start") {
            StartScreen(
                onStartButtonClicked = { navController.navigate("university_list") }
            )
        }
        composable(route = "university_list") {
            UniversityListScreen(
                universities = listOf(), // Aquí deberías pasar la lista real de universidades
                onUniversitySelected = { universityId ->
                    navController.navigate("university_detail/$universityId")
                },
                onAddUniversityClicked = { navController.navigate("add_university") }
            )
        }
        composable(route = "university_detail/{universityId}") { backStackEntry ->
            val universityId = backStackEntry.arguments?.getString("universityId")
            if (universityId != null) {
                // Aquí deberías obtener los detalles de la universidad con universityId
                UniversityDetailScreen(universityId = universityId)
            } else {
                // Manejar el caso en el que no se proporciona universityId
            }
        }
        composable(route = "add_university") {
            AddUniversityScreen(
                onUniversityAdded = {
                    // Aquí deberías agregar la lógica para agregar una nueva universidad
                    navController.popBackStack()
                }
            )
        }
    }
}
