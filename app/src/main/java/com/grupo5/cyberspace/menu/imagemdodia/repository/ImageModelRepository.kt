package com.grupo5.cyberspace.menu.imagemdodia.repository

import com.grupo5.cyberspace.data.api.NetworkUtils.Companion.MY_PUBLIC_KEY

class ImageModelRepository {

    private val client = ImageModelEndpoint.endpoint

    suspend fun obterImagemDoDia() = client.obterImagemDoDia(MY_PUBLIC_KEY)
}