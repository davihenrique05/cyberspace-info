package com.grupo5.cyberspace.menu.imagemdodia.repository

import com.grupo5.cyberspace.data.api.NetworkUtils
import com.grupo5.cyberspace.menu.imagemdodia.model.ResponseImageModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageModelEndpoint {

    @GET("planetary/apod")
    suspend fun obterImagemDoDia(@Query("api_key") api_key: String) :ResponseImageModel

    companion object{

        val endpoint: ImageModelEndpoint by lazy {
            NetworkUtils.getRetrofitInstance(NetworkUtils.BASE_URL).create(ImageModelEndpoint::class.java)
        }
    }
}