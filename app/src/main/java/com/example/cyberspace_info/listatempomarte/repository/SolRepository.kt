package com.example.cyberspace_info.listatempomarte.repository

class SolRepository {
    private val client = IInsightEndpoint.endpoint
    suspend fun obterSol() = client.obterSol()
}