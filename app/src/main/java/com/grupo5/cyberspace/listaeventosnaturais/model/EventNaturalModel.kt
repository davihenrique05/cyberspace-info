package com.grupo5.cyberspace.listaeventosnaturais.model

data class EventNaturalModel(
    
    val title:String,
    val categories: List<CategoryEventModel>,
    val geometries: List<GeometryEventModel>

)