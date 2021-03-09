package com.grupo5.cyberspace.listatempomarte.model

data class SolModel(
    val id: Int,
    val atmosphericTemperature: SolInfoModel,
    val windSpeed: SolInfoModel,
    val pressure: SolInfoModel,
    val firstUTC: String,
    val lastUTC: String,
    val season: String
)