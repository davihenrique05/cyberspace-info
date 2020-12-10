package com.example.cyberspace_info.asteroidesemcolisao.model

import com.google.gson.annotations.SerializedName

data class AsteroideModel (
    val nome:String,
    val link:String,
    val diametroMinimo:Double,
    val diametroMaximo: Double,
    val data: String,
    val velocidadeModelRel: Double
)