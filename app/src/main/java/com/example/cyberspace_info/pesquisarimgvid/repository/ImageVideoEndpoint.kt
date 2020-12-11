package com.example.cyberspace_info.pesquisarimgvid.repository

import com.example.cyberspace_info.api.MY_PUBLIC_KEY
import com.example.cyberspace_info.api.NetworkUtils
import com.example.cyberspace_info.listatecnologiasusadas.model.ProjectContainerModel
import com.example.cyberspace_info.listatecnologiasusadas.model.ResultSearchProjectModel
import com.example.cyberspace_info.listatecnologiasusadas.repository.ProjectIdEndpoint
import com.example.cyberspace_info.pesquisarimgvid.model.ContainerImageModel
import retrofit2.http.GET
import retrofit2.http.Path
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