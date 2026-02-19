package com.example.api_bbdd_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_bbdd_app.ui.screens.AddGameScreen
import com.example.api_bbdd_app.ui.screens.HomeScreen
import com.example.api_bbdd_app.ui.viewmodel.HomeViewModel
import com.example.api_bbdd_app.ui.theme.Api_bbdd_appTheme
import androidx.compose.ui.unit.dp
import com.example.api_bbdd_app.data.local.AppDatabase
import com.example.api_bbdd_app.data.local.dao.JuegoDao
import com.example.api_bbdd_app.data.local.entities.DesarrolladorEntity
import com.example.api_bbdd_app.data.local.entities.DetalleEntity
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.data.local.entities.PlataformaEntity
import com.example.api_bbdd_app.data.local.entities.relations.JuegosPlataformasCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val database by lazy { AppDatabase.getInstance(this) }

    // Y si tienes el DAO ahí mismo, haz lo mismo:
    val dao by lazy { database.getJuegoDao() }

    @SuppressLint("CoroutineCreationDuringComposition")
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