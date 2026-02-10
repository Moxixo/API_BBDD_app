package com.example.api_bbdd_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.api_bbdd_app.data.local.dao.JuegoDao
import com.example.api_bbdd_app.model.Juego
import com.example.api_bbdd_app.ui.theme.Api_bbdd_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Api_bbdd_appTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Greeting("Android")
                }
            }
        }
    }
}


@Composable
fun NoteListScreen(juegoDao: JuegoDao) {
    val notes by juegoDao.getAllGames().collectAsState(initial = emptyList())

    LazyColumn {
        items(notes) { juego ->
            Juego(0,0,"persona", "rpg")
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Api_bbdd_appTheme {
        Greeting("Android")
    }
}