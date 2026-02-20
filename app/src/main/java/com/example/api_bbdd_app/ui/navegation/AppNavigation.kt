package com.example.api_bbdd_app.ui.navegation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.api_bbdd_app.ui.screens.GameScreen
import com.example.api_bbdd_app.ui.screens.HomeScreen
import com.example.api_bbdd_app.ui.viewmodel.GameViewModel
import com.example.api_bbdd_app.ui.viewmodel.HomeViewModel

@Composable
fun AppNavigation(
    homeViewModel: HomeViewModel,
    gameViewModel: GameViewModel
) {

    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable(route = "home") {
            HomeScreen(
                viewModel = homeViewModel,
                onNavigateToGame = {
                    navController.navigate("game")
                }
            )
        }

        composable(route = "game") {
            GameScreen(
                viewModel = gameViewModel,
                // Le pasamos una función que le dice: "Cuando guardes con éxito, quita esta pantalla y vuelve atrás"
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}