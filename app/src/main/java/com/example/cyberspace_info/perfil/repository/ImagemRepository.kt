package com.example.cyberspace_info.perfil.repository

import com.example.cyberspace_info.perfil.dao.ImagemDao
import com.example.cyberspace_info.perfil.entity.ImagemEntity

class ImagemRepository(private val imagemDao: ImagemDao) {

    suspend fun salvarImagem(imagem: ImagemEntity) = imagemDao.salvarImagem(imagem)

    suspend fun obterImagems(uid:String) = imagemDao.obterImagens(uid)

    suspend fun deletarImagens(url: String,uid:String) = imagemDao.deletarImagemUrl(url,uid)
}