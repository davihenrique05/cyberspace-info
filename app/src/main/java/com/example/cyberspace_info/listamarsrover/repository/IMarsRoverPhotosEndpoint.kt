package com.example.cyberspace_info.listamarsrover.repository

import com.example.cyberspace_info.data.api.NetworkUtils
import com.example.cyberspace_info.data.api.NetworkUtils.Companion.BASE_URL
import com.example.cyberspace_info.data.api.NetworkUtils.Companion.MY_PUBLIC_KEY
import com.example.cyberspace_info.data.model.ResponseMarsRoverModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMarsRoverPhotosEndpoint {
    @GET("/mars-photos/api/v1/rovers/{rover}/photos?")
    suspend fun obterRover(
        @Path("rover") rover: String,
        @Query("earth_date") earthDate: String,
        @Query("page") page: Int = 1,
        @Query("api_key")api_key: String = MY_PUBLIC_KEY
    ): ResponseMarsRoverModel

    companion object {
        val endpoint: IMarsRoverPhotosEndpoint by lazy {
            NetworkUtils.getRetrofitInstance(BASE_URL).create(IMarsRoverPhotosEndpoint::class.java)
        }
    }
}