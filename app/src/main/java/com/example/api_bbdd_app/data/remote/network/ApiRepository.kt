package com.example.api_bbdd_app.data.remote.network

import android.util.Log
import com.example.api_bbdd_app.model.Juego
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class ApiRepository {

    //Pasamos el cliente definido en ApiService
    private val cliente = ApiService.cliente

    //Pasamos la ruta de juegos
    private val ruta = ApiRoutes.juegos


    //Funcion que devuelve una lista con los juegos cargados de la API
    suspend fun getJuegos(): List<Juego> {

        return try {

            //Pedimos la peticion mediante el cliente a la API
            val respuesta = cliente.get(ruta)

            //Si la respuesta ha sido exitossa devuelveme el body de la respuesta con ese formato
            if (respuesta.status.isSuccess()) {
                return respuesta.body<List<Juego>>()
            }
            //Si no devuelveme una lista vacia
            else {
                return emptyList()
            }

        } catch (e: Exception) {
            //En caso de cualquier error devuelve lista vacia
            Log.e("API_ERROR", "Error descargando juegos: ${e.message}")
            emptyList()
        }

    }

    //Funcion para actualizar un juego
    suspend fun updateJuego(juego: Juego) {

        //Haz una peticion PUT a un Juego especifico creando un cuerpo
        // con los datos de un juego pasado por parametros
        cliente.put("${ruta}/${juego.juego_id}") { setBody(juego) }

    }

    //Funcion para agregar un juego
    suspend fun addJuego(juego: Juego) {

        //Realiza una peticion post a la ruta de juegos
        cliente.post(ruta){

            //Definimos el tipo de contenido para representar
            contentType(ContentType.Application.Json)
            //Definimos el cuerpo de la peticion (el juego vaya)
            setBody(juego)
        }

        println("Juego añadido correctamente a la API")

    }

    //Funcion para eliminar juego
    suspend fun deleteJuego(juego:Juego) {

        //Haz una peticion de borrado a un juego especifico
        cliente.delete("${ruta}/${juego.juego_id}")
        println("Borrado juego de la API")
    }

}




