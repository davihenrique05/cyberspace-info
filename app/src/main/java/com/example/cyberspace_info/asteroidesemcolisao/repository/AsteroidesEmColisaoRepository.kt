package com.example.cyberspace_info.asteroidesemcolisao.repository

import com.example.cyberspace_info.api.MY_PUBLIC_KEY

class AsteroidesEmColisaoRepository {

    val client = AsteroidesEmColisaoEndpoint.Endpoint

    suspend fun obterListaDeAsteroides(startDate:String, end_date:String) =
        client.obterListaDeAsteroides(
            startDate,
            end_date,
            MY_PUBLIC_KEY
        )
}