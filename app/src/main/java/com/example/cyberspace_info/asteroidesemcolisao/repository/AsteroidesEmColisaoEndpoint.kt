package com.example.cyberspace_info.asteroidesemcolisao.repository

import com.example.cyberspace_info.api.NetworkUtils
import com.example.cyberspace_info.asteroidesemcolisao.model.ResponseAsteroidModel
import com.example.cyberspace_info.listaeventosnaturais.repository.EventosNaturaisEndpoint
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidesEmColisaoEndpoint {

    @GET("feed")
    suspend fun obterListaDeAsteroides(
        @Query("start_date") start_date:String,
        @Query("end_date") end_date:String,
        @Query("api_key") api_key:String
    ):ResponseAsteroidModel

    companion object{
        const val BASE_URL_ASTEROIDES = "https://api.nasa.gov/neo/rest/v1/"

        val Endpoint: AsteroidesEmColisaoEndpoint by lazy {
            NetworkUtils.getRetrofitInstance(AsteroidesEmColisaoEndpoint.BASE_URL_ASTEROIDES).create(AsteroidesEmColisaoEndpoint::class.java)
        }
    }
}