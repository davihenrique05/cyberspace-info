package com.example.cyberspace_info.pesquisarimgvid.repository

import com.example.cyberspace_info.data.api.NetworkUtils
import com.example.cyberspace_info.pesquisarimgvid.model.ContainerImageModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageVideoEndpoint {

    @GET("search")
    suspend fun getUrlsImages(@Query("q")q:String) : ContainerImageModel

    companion object{

        val BASE_URL = "https://images-api.nasa.gov/"
        val Endpoint: ImageVideoEndpoint by lazy {
            NetworkUtils.getRetrofitInstance(BASE_URL).create(ImageVideoEndpoint::class.java)
        }

    }

}