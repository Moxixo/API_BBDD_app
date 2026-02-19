package com.example.api_bbdd_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_bbdd_app.ui.screens.AddGameScreen
import com.example.api_bbdd_app.ui.screens.HomeScreen
import com.example.api_bbdd_app.ui.viewmodel.HomeViewModel
import com.example.api_bbdd_app.ui.theme.Api_bbdd_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Api_bbdd_appTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    //ViewModel de HomeScreen
                    val viewModel: HomeViewModel = viewModel()
                    //Variable auxiliar para porbar el findByID
                    val juego by viewModel.juego

                    Column(modifier = Modifier.padding(innerPadding)){

                        //Iniciamos como prueba HomeScreen passando el viewmodel (esto deberia hacerse en navegacion )
                        HomeScreen(viewModel)

                    }

                }
            }
        }
    }
}
