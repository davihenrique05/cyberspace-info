package com.example.cyberspace_info.asteroidesemcolisao.repository

import com.example.cyberspace_info.data.api.NetworkUtils
import com.example.cyberspace_info.asteroidesemcolisao.model.ResponseAsteroidModel
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidesEndpoint {

    @GET("feed")
    suspend fun obterListaDeAsteroides(
        @Query("start_date") start_date:String,
        @Query("end_date") end_date:String,
        @Query("api_key") api_key:String
    ):ResponseAsteroidModel

    companion object{
        const val BASE_URL_ASTEROIDES = "https://api.nasa.gov/neo/rest/v1/"

        val endpoint: AsteroidesEndpoint by lazy {
            NetworkUtils.getRetrofitInstance(BASE_URL_ASTEROIDES).create(AsteroidesEndpoint::class.java)
        }
    }
}