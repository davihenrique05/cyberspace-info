package com.example.cyberspace_info.imagelandscape.repository

import com.example.cyberspace_info.imagelandscape.dao.ImagemDao
import com.example.cyberspace_info.imagelandscape.entity.ImagemEntity

class ImagemRepository(private val imagemDao: ImagemDao) {

    suspend fun salvarImagem(imagem: ImagemEntity) = imagemDao.salvarImagem(imagem)

    suspend fun obterImagems() = imagemDao.obterImagens()

    suspend fun deletarImagens(url: String) = imagemDao.deletarImagemUrl(url)
}