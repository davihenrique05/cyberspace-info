package com.example.cyberspace_info.listatempomarte.repository

import com.example.cyberspace_info.data.api.NetworkUtils
import com.example.cyberspace_info.data.api.NetworkUtils.Companion.BASE_URL
import com.example.cyberspace_info.data.api.NetworkUtils.Companion.MY_PUBLIC_KEY
import com.google.gson.internal.LinkedTreeMap
import retrofit2.http.GET
import retrofit2.http.Query

interface IInsightEndpoint {
    @GET("/insight_weather/?")
    suspend fun obterSol(
        @Query("api_key") api_key: String = MY_PUBLIC_KEY,
        @Query("feedtype") feedtype: String = "json",
        @Query("ver") ver: String = "1.0"
    ): Any

    companion object {
        val endpoint: IInsightEndpoint by lazy {
            NetworkUtils.getRetrofitInstance(BASE_URL).create(IInsightEndpoint::class.java)
        }
    }
}