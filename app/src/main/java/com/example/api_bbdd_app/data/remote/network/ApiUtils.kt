package com.example.api_bbdd_app.data.remote.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

//Objeto que contiene las rutas de la API

object ApiRoutes {
    //Ruta raiz de la API creada
    val raiz : String = "https://my-json-server.typicode.com/Moxixo/pmd-api"

    //Ruta relativa a los datos de juegos
    val juegos : String = "${raiz}/juegos"


}


//Objeto que configura el cliente HTTP
object ApiService {

    //Variable que guarda el "Motor" que manipula los datos con configuraciones basicas
    val cliente = HttpClient(Android) {

        //Hay que instalar manualmente el ContentNegotation que es el traductor de JSON a KT OBJECT
        install(ContentNegotiation) {

            json(Json {

                //Formatea el Json para que se vea bonito
                prettyPrint = true
                //Ignora campos extra no definidos en la data class
                ignoreUnknownKeys = true
                //Es mas permisivo con el formato de JSON para evitar errores
                isLenient = true
            })
        }

        // Para Registro (Logging)
        install(Logging) {
            level = LogLevel.ALL
        }


    }
}