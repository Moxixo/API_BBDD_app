package com.example.api_bbdd_app.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiService {

    //Variable que guarda el "Motor" que manipula los datos con configuraciones basicas
    val cliente = HttpClient(Android) {

        //Hay que instalar manualmente el ContentNegotation que es el traductor de JSON a KT OBJECT
        install(ContentNegotiation) {

            json(Json {

                //Ignora campos extra no definidos en la data class
                ignoreUnknownKeys = true
                //Es mas permisivo con el formato de JSON para evitar errores
                isLenient = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000 // Si en 5 segundos no responde, dará error
        }

    }
}