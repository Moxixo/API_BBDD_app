package com.example.api_bbdd_app.data.remote.network

import android.util.Log
import com.example.api_bbdd_app.data.remote.ApiService
import com.example.api_bbdd_app.model.Juego
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.isSuccess

class ApiRepository {

    //Pasamos el cliente definido en ApiService
    private val cliente = ApiService.cliente
    //Pasamos la ruta de juegos
    private val ruta = ApiRoutes.juegos


    suspend fun getJuegos() : List<Juego>{


        return try {

            //Pedimos la peticion mediante el cliente a la API
            val respuesta = cliente.get(ruta)

            //Si la respuesta ha sido exitossa devuelveme el body de la respuesta con ese formato
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

    suspend fun findJuego(id : Int) : Juego? {

        try {

            //Haz get de un solo juego
            val respuesta = cliente.get("${ruta}/${id}")

            //Si la respuesta ha sido exitosa devuelveme el objeto
            if(respuesta.status.isSuccess()){
                return respuesta.body()
            }
            //Si no devuelveme nulo
            else {
                return null
            }
        }
        catch (e : Exception){

            //Si algo falla devuelve nulo
            println(e.message)
            return null
        }
    }

    suspend fun updateJuego(id:Int, juego: Juego): Juego {

        //Haz una peticion PUT a un Juego especifico creando un cuerpo
        // con los datos de un juego pasado por parametros
        return cliente.put("${ruta}/$id") { setBody(juego) }.body()

    }

    suspend fun deleteJuego(id:Int) {

        //Haz una peticion de borrado a un juego especifico
        cliente.delete("${ruta}/$id")
    }
}
