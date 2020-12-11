package com.example.cyberspace_info.data.model

import com.example.cyberspace_info.listamarsrover.model.MarsRoverPhotosModel
import com.google.gson.annotations.SerializedName

data class ResponseMarsRoverModel (
    @SerializedName("photos")
    val fotos: List<MarsRoverPhotosModel>
)