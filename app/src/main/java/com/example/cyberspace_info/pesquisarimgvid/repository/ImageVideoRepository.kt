package com.example.cyberspace_info.pesquisarimgvid.repository

import com.example.cyberspace_info.api.MY_PUBLIC_KEY
import com.example.cyberspace_info.listatecnologiasusadas.repository.ProjectIdEndpoint

class ImageVideoRepository {

    val client = ImageVideoEndpoint.Endpoint

    suspend fun getUrlsImages(search:String) = client.getUrlsImages(search)

}