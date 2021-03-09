package com.grupo5.cyberspace.asteroidesemcolisao.repository

import com.grupo5.cyberspace.data.api.NetworkUtils.Companion.MY_PUBLIC_KEY

class AsteroidesRepository {

    private val client = AsteroidesEndpoint.endpoint

    suspend fun obterListaDeAsteroides(startDate:String, end_date:String) =
        client.obterListaDeAsteroides(
            startDate,
            end_date,
            MY_PUBLIC_KEY
        )
}