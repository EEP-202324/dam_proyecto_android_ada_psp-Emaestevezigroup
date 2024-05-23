package com.example.universidades

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.universidades.models.University
import com.example.universidades.ui.screen.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.universidades.api.UniversityApi
import com.example.universidades.ui.theme.UniversityColors
import com.example.universidades.viewmodels.UniversityUiState
import com.example.universidades.viewmodels.UniversityViewModel
import com.example.universidades.ui.screen.SearchScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversityApp(navController: NavHostController = rememberNavController()) {
    MaterialTheme(
        colors = UniversityColors,
        typography = Typography()
    ) {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

        val universityViewModel = UniversityViewModel(UniversityApi.retrofitService)

        val universityUiState by universityViewModel.universityUiState.observeAsState()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                UniversityTopAppBar(
                    showBackButton = currentRoute != "start",
                    onBackButtonClicked = { navController.popBackStack() },
                    onSearchButtonClicked = { navController.navigate("search") }
                )

            }
        ) {
            Column {
                Spacer(modifier = Modifier.height(35.dp))

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (currentRoute) {
                        "university_list" -> {
                            val searchResultsList: List<University> =
                                (universityUiState as? UniversityUiState.Success)?.universities
                                    ?: emptyList()

                            UniversityListScreen(
                                universities = searchResultsList,
                                onDeleteUniversityClicked = { university ->
                                    university.id?.let { it1 ->
                                        universityViewModel.deleteUniversity(
                                            it1
                                        )
                                    }
                                },
                                onAddUniversityClicked = { navController.navigate("add_university") },
                                onUniversitySelected = { university ->
                                    navController.navigate(
                                        "university_detail/${university.id}/${university.name}/${university.address}/${university.phoneNumber}/${university.email}/${university.hasScholarship}/${university.location.name}/${university.category.name}"
                                    )
                                },
                                universityViewModel = universityViewModel
                            )
                        }

                        else -> {
                            UniversityContent(
                                universityUiState = universityUiState ?: UniversityUiState.Loading,
                                navController = navController,
                                universityViewModel = universityViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UniversityContent(
    universityUiState: UniversityUiState,
    navController: NavHostController,
    universityViewModel: UniversityViewModel
) {
    NavHost(navController = navController, startDestination = "start") {
        composable(route = "start") {
            StartScreen(
                onStartButtonClicked = { navController.navigate("university_list") }
            )
        }

        composable(route = "university_list") {
            UniversityListScreen(
                universities = (universityUiState as? UniversityUiState.Success)?.universities ?: emptyList(),
                onDeleteUniversityClicked = { university ->
                    university.id?.let { it1 -> universityViewModel.deleteUniversity(it1) }
                },
                onAddUniversityClicked = { navController.navigate("add_university") },
                onUniversitySelected = { university ->
                    navController.navigate(
                        "university_detail/${university.id}/${university.name}/${university.address}/${university.phoneNumber}/${university.email}/${university.hasScholarship}/${university.location.name}/${university.category.name}"
                    )
                },
                universityViewModel = universityViewModel,
            )
        }

        composable(
            route = "university_detail/{universityId}/{name}/{address}/{phoneNumber}/{email}/{hasScholarship}/{location}/{category}",
            arguments = listOf(
                navArgument("universityId") { type = NavType.LongType },
                navArgument("name") { type = NavType.StringType },
                navArgument("address") { type = NavType.StringType },
                navArgument("phoneNumber") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
                navArgument("hasScholarship") { type = NavType.BoolType },
                navArgument("location") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val universityId = backStackEntry.arguments?.getLong("universityId")
            val name = backStackEntry.arguments?.getString("name")
            val address = backStackEntry.arguments?.getString("address")
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber")
            val email = backStackEntry.arguments?.getString("email")
            val hasScholarship = backStackEntry.arguments?.getBoolean("hasScholarship") ?: false
            val locationName = backStackEntry.arguments?.getString("location")
            val category = backStackEntry.arguments?.getString("category")

            val location = locationName?.let { universityViewModel.getLocationByName(it) }
            val categoryName = category?.let { universityViewModel.getCategoryByName(it) }

            if (universityId != null && name != null && address != null && phoneNumber != null && email != null && location != null && categoryName != null) {
                val university = University(
                    id = universityId,
                    name = name,
                    address = address,
                    phoneNumber = phoneNumber,
                    email = email,
                    hasScholarship = hasScholarship,
                    location = location,
                    category = categoryName
                )
                UniversityDetailScreen(
                    university = university,
                    onUpdateUniversityClicked = { updatedUniversity ->
                        universityViewModel.updateUniversity(updatedUniversity)
                    }
                )
            } else {
                Text(text = "Error: Faltan parámetros de la universidad")
            }
        }

        composable(route = "add_university") {
            AddUniversityScreen(navController = navController, universityViewModel = universityViewModel, university = null)
        }

        composable(route = "search") {
            SearchScreen(navController = navController, universityViewModel = universityViewModel)
        }

        composable(route = "search_results") {
            UniversitySearchResultsScreen(
                searchResults = (universityUiState as? UniversityUiState.Success)?.universities ?: emptyList(),
                onUniversitySelected = { university ->
                    navController.navigate(
                        "university_detail/${university.id}/${university.name}/${university.address}/${university.phoneNumber}/${university.email}/${university.hasScholarship}/${university.location.name}/${university.category.name}"
                    )
                }
            ) { university ->
                university.id?.let { it1 -> universityViewModel.deleteUniversity(it1) }
            }
        }
    }
}


@Composable
fun UniversityTopAppBar(
    showBackButton: Boolean,
    onBackButtonClicked: () -> Unit,
    onSearchButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = "Evento Aula") },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { onBackButtonClicked() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button))
                }
            }
        },
        actions = {
            IconButton(onClick = onSearchButtonClicked) {
                Icon(Icons.Filled.Search, contentDescription = stringResource(R.string.search_button))
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = modifier
    )
}
