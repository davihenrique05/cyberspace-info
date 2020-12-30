package com.example.cyberspace_info.pesquisarimgvid.repository


class ImageVideoRepository {

    private val client = ImageVideoEndpoint.Endpoint

    suspend fun getUrlsImages(search:String) = client.getUrlsImages(search)

}