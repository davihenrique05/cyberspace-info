package com.grupo5.cyberspace.listatecnologiasusadas.repository

import com.grupo5.cyberspace.data.api.NetworkUtils
import com.grupo5.cyberspace.listatecnologiasusadas.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectIdEndpoint {

    @GET("projects")
    suspend fun getAllIdsProjects(@Query("api_key")api_key:String) : ProjectContainerModel

    @GET("projects/{id_parameter}")
    suspend fun getUniqueObjectProject(@Path("id_parameter")id_parameter:Int,
                                       @Query("updatedSince")updatedSince:String,
                                       @Query("api_key")api_key:String
                                       ) : ResultSearchProjectModel

    companion object{

        private const val BASE_URL = "https://api.nasa.gov/techport/api/"
        val Endpoint:ProjectIdEndpoint by lazy {
            NetworkUtils.getRetrofitInstance(BASE_URL).create(ProjectIdEndpoint::class.java)
        }

    }

}