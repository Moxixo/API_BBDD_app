package com.example.api_bbdd_app.ui.navegation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.api_bbdd_app.ui.screens.GameScreen
import com.example.api_bbdd_app.ui.screens.HomeScreen
import com.example.api_bbdd_app.ui.viewmodel.GameViewModel
import com.example.api_bbdd_app.ui.viewmodel.HomeViewModel

@Composable
fun AppNavigation(
    homeViewModel: HomeViewModel,
    gameViewModel: GameViewModel,
) {

    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController, startDestination = "home"
    ) {
        //HOME
        composable(route = "home") {
            HomeScreen(
                viewModel = homeViewModel, onNavigateToGame = { juegoId ->
                    if (juegoId != null) {

                        navController.navigate("game?juegoId=$juegoId")
                    } else {

                        navController.navigate("game")
                    }
                })
        }

        //GAME -> GAME TIENE ID?, si tiene, me envias ahí
        composable(route = "game?juegoId={juegoId}", listOf(navArgument("juegoId") {
            type = NavType.LongType
            defaultValue = -1L
        })) { backStackEntry ->
            //argumento necesario para navegar a la pagina concreta de juego (si está registrado)
            val argumentoId = backStackEntry.arguments?.getLong("juegoId") ?: -1L
            // Lo convertimos de nuevo a nulo si es -1, para que tu GameScreen lo entienda
            val idParaPantalla: Long? = if (argumentoId == -1L) null else argumentoId
            GameScreen(
                juegoId = idParaPantalla,
                viewModel = gameViewModel,
                // volvemos atrás
                onNavigateBack = {
                    navController.popBackStack()
                })
        }
    }
}