package com.grupo5.cyberspace.perfil.repository

import com.grupo5.cyberspace.perfil.dao.ImagemDao
import com.grupo5.cyberspace.perfil.entity.ImagemEntity

class ImagemRepository(private val imagemDao: ImagemDao) {

    suspend fun salvarImagem(imagem: ImagemEntity) = imagemDao.salvarImagem(imagem)

    suspend fun obterImagems(uid:String) = imagemDao.obterImagens(uid)

    suspend fun deletarImagens(url: String,uid:String) = imagemDao.deletarImagemUrl(url,uid)
}