package com.example.cyberspace_info.asteroidesemcolisao.model

data class Asteroide (
    //"name"
    val nome:String,
    //"nasa_jpl_url"
    val link:String,
    //"estimated_diameter_min"
    val diametroMinimo:Double,
    //"estimated_diameter_max"
    val diametroMaximo: Double,
    //"close_approach_date"
    val data: String,
    //"relative_velocity"
    val velocidadeRel: Velocidade
)