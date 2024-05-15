package com.example.universidades

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.universidades.ui.screen.AddUniversityScreen
import com.example.universidades.ui.screen.StartScreen
import com.example.universidades.ui.screen.UniversityDetailScreen
import com.example.universidades.ui.screen.UniversityListScreen
import com.example.universidades.viewmodels.UniversityUiState
import com.example.universidades.viewmodels.UniversityViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversityApp(navController: NavHostController = rememberNavController()) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val universityViewModel: UniversityViewModel = viewModel()
    val universityUiState by universityViewModel.universityUiState.observeAsState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { UniversityTopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Column {
            Spacer(modifier = Modifier.height(35.dp))

            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                UniversityContent(
                    universityUiState = universityUiState ?: UniversityUiState.Loading,
                    navController = navController,
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversityTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text(text = "Universidades") },
        navigationIcon = {
            IconButton(onClick = { /* Manejar la navegación hacia atrás o abrir el menú lateral */ }) {
                Icon(Icons.Filled.Menu, contentDescription = "Menú")
            }
        },
        actions = {
            // Agregar acciones adicionales aquí, como botones de búsqueda o de perfil
            IconButton(onClick = { /* Acción 1 */ }) {
                Icon(Icons.Filled.Search, contentDescription = "Buscar")
            }
        },
        backgroundColor = MaterialTheme.colors.primary, // Color de fondo de la barra
        contentColor = MaterialTheme.colors.onPrimary, // Color del contenido de la barra
        modifier = modifier
    )
}


@Composable
fun UniversityContent(universityUiState: UniversityUiState, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "start") {
        composable(route = "start") {
            StartScreen(
                onStartButtonClicked = { navController.navigate("university_list") }
            )
        }
        composable(route = "university_list") {
            UniversityListScreen(
                universities = (universityUiState as? UniversityUiState.Success)?.universities ?: emptyList(),
                onUniversitySelected = { universityId ->
                    navController.navigate("university_detail/$universityId")
                },
                onAddUniversityClicked = { navController.navigate("add_university") }
            )
        }

        composable(route = "university_detail/{universityId}") { backStackEntry ->
            val universityId = backStackEntry.arguments?.getString("universityId")
            if (universityId != null) {
                UniversityDetailScreen(universityId = universityId)
            } else {
                Text(text = "Error: ID de universidad no proporcionado")
            }
        }

        composable(route = "add_university") {
            val universityViewModel: UniversityViewModel = viewModel()
            AddUniversityScreen(navController = navController, universityViewModel = universityViewModel)
        }

    }
}
