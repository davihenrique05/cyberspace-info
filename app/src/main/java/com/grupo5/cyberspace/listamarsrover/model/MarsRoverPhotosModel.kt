package com.grupo5.cyberspace.listamarsrover.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class MarsRoverPhotosModel (
    @SerializedName("id")
    val id: Int,
    @SerializedName("sol")
    val sol: Int,
    @SerializedName("img_src")
    val imagemURL: String,
    @SerializedName("earth_date")
    val data: Date
)