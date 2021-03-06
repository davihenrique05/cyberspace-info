package com.grupo5.cyberspace.asteroidesemcolisao.model

import com.google.gson.annotations.SerializedName

data class ResponseAsteroidModel (
    @SerializedName("links")
    val navigation : LinksModel,
    @SerializedName("element_count")
    val total: Int,
    @SerializedName("near_earth_objects")
    val asteiroides: Any
)