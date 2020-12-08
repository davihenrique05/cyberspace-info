package com.example.cyberspace_info.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val MY_PUBLIC_KEY =  "4AiyYmQ8TxVM4EJTawZBapcWhOcCAofFy2nRtIgm"

class NetworkUtils {

    companion object{

        const val BASE_URL = "https://api.nasa.gov/"

        fun getRetrofitInstance() : Retrofit {
            return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        }

    }

}