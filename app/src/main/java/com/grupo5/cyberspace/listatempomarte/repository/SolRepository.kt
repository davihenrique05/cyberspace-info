package com.grupo5.cyberspace.listatempomarte.repository

class SolRepository {
    private val client = IInsightEndpoint.endpoint
    suspend fun obterSol() = client.obterSol()
}