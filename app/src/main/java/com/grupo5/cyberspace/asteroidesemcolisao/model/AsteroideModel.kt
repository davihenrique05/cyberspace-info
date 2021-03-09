package com.grupo5.cyberspace.asteroidesemcolisao.model

data class AsteroideModel (
    val nome:String,
    val link:String,
    val diametroMinimo:Double,
    val diametroMaximo: Double,
    val data: String,
    val velocidadeModelRel: Double,
    val distancia: Double
)