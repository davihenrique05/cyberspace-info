package com.example.cyberspace_info.listaeventosnaturais.repository

import com.example.cyberspace_info.data.api.NetworkUtils.Companion.MY_PUBLIC_KEY

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