package com.example.cyberspace_info.menu.imagemdodia.repository

import com.example.cyberspace_info.api.MY_PUBLIC_KEY

class ImageModelRepository {

    val client = ImageModelEndpoint.endpoint

    suspend fun obterImagemDoDia() = client.obterImagemDoDia(MY_PUBLIC_KEY)
}