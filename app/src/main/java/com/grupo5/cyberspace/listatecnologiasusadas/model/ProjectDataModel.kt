package com.grupo5.cyberspace.listatecnologiasusadas.model

data class ProjectDataModel(
    val id: Int,
    val lastUpdated:String,
    val title:String,
    val status:String,
    val startDate:String,
    val endDate:String,
    val description:String
)