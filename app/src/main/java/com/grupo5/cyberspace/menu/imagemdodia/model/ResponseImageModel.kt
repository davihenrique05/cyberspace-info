package com.grupo5.cyberspace.menu.imagemdodia.model

import com.google.gson.annotations.SerializedName

data class ResponseImageModel(
    @SerializedName("title")
    val tittle: String,
    val url:String,
    @SerializedName("media_type")
    val midia:String
)