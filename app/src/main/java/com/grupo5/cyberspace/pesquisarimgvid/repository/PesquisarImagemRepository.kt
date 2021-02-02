package com.grupo5.cyberspace.pesquisarimgvid.repository


class PesquisarImagemRepository {

    private val client = PesquisarImagemEndpoint.Endpoint

    suspend fun getUrlsImages(search:String) = client.getUrlsImages(search)

}