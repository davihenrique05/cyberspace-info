package com.grupo5.cyberspace.perfil.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.grupo5.cyberspace.perfil.entity.ImagemEntity

@Dao
interface ImagemDao {

    @Insert
    suspend fun salvarImagem(imagem: ImagemEntity)

    @Query("SELECT * FROM Imagens WHERE uid = :uid")
    suspend fun obterImagens(uid:String) : MutableList<ImagemEntity>

    @Query("DELETE FROM Imagens WHERE url = :url AND uid = :uid")
    suspend fun deletarImagemUrl (url: String, uid:String)
}