package com.example.cyberspace_info.pesquisarimgvid.repository


class ImageVideoRepository {

    val client = ImageVideoEndpoint.Endpoint

    suspend fun getUrlsImages(search:String) = client.getUrlsImages(search)

}