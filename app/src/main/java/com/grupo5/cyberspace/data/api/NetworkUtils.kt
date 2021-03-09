package com.grupo5.cyberspace.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {

    companion object{
        const val MY_PUBLIC_KEY =  "4AiyYmQ8TxVM4EJTawZBapcWhOcCAofFy2nRtIgm"
        const val BASE_URL =  "https://api.nasa.gov/"

        fun getRetrofitInstance(baseUrl : String) : Retrofit {
            return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        }

    }

}