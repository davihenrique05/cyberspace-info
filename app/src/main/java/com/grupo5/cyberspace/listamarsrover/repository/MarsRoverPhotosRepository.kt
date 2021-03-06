package com.grupo5.cyberspace.listamarsrover.repository

class MarsRoverPhotosRepository {
    private val client = IMarsRoverPhotosEndpoint.endpoint
    suspend fun obterRover(rover: String, earthDate: String, page: Int = 1) =
        client.obterRover(rover, earthDate, page)
}