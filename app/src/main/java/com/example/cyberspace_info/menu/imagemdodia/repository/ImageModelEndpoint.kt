package com.example.cyberspace_info.menu.imagemdodia.repository

import com.example.cyberspace_info.data.api.NetworkUtils
import com.example.cyberspace_info.menu.imagemdodia.model.ResponseImageModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageModelEndpoint {

    @GET("apod")
    suspend fun obterImagemDoDia(@Query("api_key") api_key: String) :ResponseImageModel

    companion object{
        const val BASE_URL_IMAGE = "https://api.nasa.gov/planetary/"

        val endpoint: ImageModelEndpoint by lazy {
            NetworkUtils.getRetrofitInstance(BASE_URL_IMAGE).create(ImageModelEndpoint::class.java)
        }
    }
}