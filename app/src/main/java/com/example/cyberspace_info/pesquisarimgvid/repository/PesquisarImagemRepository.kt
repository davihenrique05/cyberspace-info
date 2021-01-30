package com.example.cyberspace_info.pesquisarimgvid.repository


class PesquisarImagemRepository {

    private val client = PesquisarImagemEndpoint.Endpoint

    suspend fun getUrlsImages(search:String) = client.getUrlsImages(search)

}