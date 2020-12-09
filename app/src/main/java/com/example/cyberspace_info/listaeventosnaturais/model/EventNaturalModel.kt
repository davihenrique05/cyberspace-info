package com.example.cyberspace_info.listaeventosnaturais.model

data class EventNaturalModel(
    
    val title:String,
    val categories: List<CategoryEventModel>,
    val geometries: List<GeometryEventModel>

)