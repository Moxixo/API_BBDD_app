package com.example.api_bbdd_app.data.remote.network

import android.util.Log
import com.example.api_bbdd_app.data.remote.ApiService
import com.example.api_bbdd_app.model.Juego
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess

class ApiRepository {

    //Pasamos el cliente definido en ApiService
    private val cliente = ApiService.cliente
    private val ruta = ApiRoutes.juegos


    suspend fun getJuegos() : List<Juego>{


        return try {

            //Pedimos la peticion mediante el cliente a la API
            val respuesta = cliente.get(ruta)

            //Si la respuesta ha sido exitossa devuelveme el body de la respuesta
            if(respuesta.status.isSuccess()){
                return respuesta.body<List<Juego>>()
            }
            //Si no devuelveme una lista vacia
            else {
                return emptyList()
            }

        }
        catch(e: Exception){
            //En caso de cualquier error devuelve lista vacia
            Log.e("API_ERROR", "Error descargando juegos: ${e.message}")
            emptyList()
        }

    }

}