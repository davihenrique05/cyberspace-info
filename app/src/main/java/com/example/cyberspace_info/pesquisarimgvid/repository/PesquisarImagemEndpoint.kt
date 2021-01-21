package com.example.cyberspace_info.pesquisarimgvid.repository

import com.example.cyberspace_info.data.api.NetworkUtils
import com.example.cyberspace_info.pesquisarimgvid.model.ContainerImageModel
import retrofit2.http.GET
import retrofit2.http.Query

interface PesquisarImagemEndpoint {

    @GET("search")
    suspend fun getUrlsImages(@Query("q")q:String) : ContainerImageModel

    companion object{

        val BASE_URL = "https://images-api.nasa.gov/"
        val Endpoint: PesquisarImagemEndpoint by lazy {
            NetworkUtils.getRetrofitInstance(BASE_URL).create(PesquisarImagemEndpoint::class.java)
        }

    }

}