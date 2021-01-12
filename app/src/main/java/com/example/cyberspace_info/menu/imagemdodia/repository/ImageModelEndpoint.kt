package com.example.cyberspace_info.menu.imagemdodia.repository

import com.example.cyberspace_info.data.api.NetworkUtils
import com.example.cyberspace_info.menu.imagemdodia.model.ResponseImageModel
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