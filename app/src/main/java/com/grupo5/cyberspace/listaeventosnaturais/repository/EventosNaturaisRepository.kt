package com.grupo5.cyberspace.listaeventosnaturais.repository

import com.grupo5.cyberspace.data.api.NetworkUtils.Companion.MY_PUBLIC_KEY

class EventosNaturaisRepository {

    private var client = EventosNaturaisEndpoint.Endpoint

    suspend fun getListEvents(numberDays: Int, limitEvent: Int, status: String) =
        client.getListEvents(

            numberDays,
            MY_PUBLIC_KEY,
            limitEvent,
            status
        )

}