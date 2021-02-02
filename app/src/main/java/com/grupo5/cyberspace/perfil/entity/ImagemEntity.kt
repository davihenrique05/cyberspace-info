package com.grupo5.cyberspace.perfil.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Imagens")
data class ImagemEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo
    val url: String,
    @ColumnInfo
    val uid: String
)