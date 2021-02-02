package com.grupo5.cyberspace.pesquisarimgvid.repository

import com.grupo5.cyberspace.data.api.NetworkUtils
import com.grupo5.cyberspace.pesquisarimgvid.model.ContainerImageModel
import retrofit2.http.GET
import retrofit2.http.Query

interface PesquisarImagemEndpoint {

    @GET("search")
    suspend fun getUrlsImages(@Query("q")q:String) : ContainerImageModel

    companion object{

        private const val BASE_URL = "https://images-api.nasa.gov/"
        val Endpoint: PesquisarImagemEndpoint by lazy {
            NetworkUtils.getRetrofitInstance(BASE_URL).create(PesquisarImagemEndpoint::class.java)
        }

    }

}