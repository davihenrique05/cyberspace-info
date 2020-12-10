package com.example.cyberspace_info.menu.imagemdodia.model

import com.google.gson.annotations.SerializedName

data class ResponseImageModel(
    @SerializedName("title")
    val tittle: String,
    val url:String
)