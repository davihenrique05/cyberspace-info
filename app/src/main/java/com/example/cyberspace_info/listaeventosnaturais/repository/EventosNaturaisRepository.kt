package com.example.cyberspace_info.listaeventosnaturais.repository

import com.example.cyberspace_info.api.MY_PUBLIC_KEY
import com.example.cyberspace_info.listaeventosnaturais.model.EventoNaturalContainerModel
import retrofit2.http.Query

class EventosNaturaisRepository {

    var client = EventosNaturaisEndpoint.Endpoint

    suspend fun getListEvents(numberDays:Int,limitEvent:Int,status:String) = client.getListEvents(

        numberDays,
        MY_PUBLIC_KEY,
        limitEvent,
        status)

}