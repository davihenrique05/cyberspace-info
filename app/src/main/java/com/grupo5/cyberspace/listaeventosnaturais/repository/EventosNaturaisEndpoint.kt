package com.grupo5.cyberspace.listaeventosnaturais.repository

import com.grupo5.cyberspace.data.api.NetworkUtils
import com.grupo5.cyberspace.listaeventosnaturais.model.EventoNaturalContainerModel
import retrofit2.http.GET
import retrofit2.http.Query

interface EventosNaturaisEndpoint {

    @GET("events")
    suspend fun getListEvents(
        @Query("days")days:Int?,
        @Query("api_key")api_key:String,
        @Query("limit")limit:Int,
        @Query("status")status:String
        ) : EventoNaturalContainerModel

    companion object {
        private const val BASE_URL_EONET = "https://eonet.sci.gsfc.nasa.gov/api/v2.1/"
        val Endpoint: EventosNaturaisEndpoint by lazy {
            NetworkUtils.getRetrofitInstance(BASE_URL_EONET).create(EventosNaturaisEndpoint::class.java)
        }
    }


}