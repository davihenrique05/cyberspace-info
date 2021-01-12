package com.example.cyberspace_info.imagelandscape.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cyberspace_info.imagelandscape.entity.ImagemEntity

@Dao
interface ImagemDao {

    @Insert
    suspend fun salvarImagem(imagem: ImagemEntity)

    @Query("SELECT * FROM Imagens")
    suspend fun obterImagens() : MutableList<ImagemEntity>

    @Query("DELETE FROM Imagens WHERE url = :url")
    suspend fun deletarImagemUrl (url: String)
}