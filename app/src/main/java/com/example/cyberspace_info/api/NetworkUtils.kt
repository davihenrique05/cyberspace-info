package com.example.cyberspace_info.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val MY_PUBLIC_KEY =  "4AiyYmQ8TxVM4EJTawZBapcWhOcCAofFy2nRtIgm"

class NetworkUtils {

    companion object{

        fun getRetrofitInstance(BASE_URL : String) : Retrofit {
            return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        }

    }

}