package com.grupo5.cyberspace.data.model

import com.grupo5.cyberspace.listamarsrover.model.MarsRoverPhotosModel
import com.google.gson.annotations.SerializedName

data class ResponseMarsRoverModel (
    @SerializedName("photos")
    val fotos: List<MarsRoverPhotosModel>
)