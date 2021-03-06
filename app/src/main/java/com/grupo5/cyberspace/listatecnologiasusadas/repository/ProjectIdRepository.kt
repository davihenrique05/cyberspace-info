package com.grupo5.cyberspace.listatecnologiasusadas.repository

import com.grupo5.cyberspace.data.api.NetworkUtils.Companion.MY_PUBLIC_KEY

class ProjectIdRepository {

    private val client = ProjectIdEndpoint.Endpoint

    suspend fun getAllIdsProjects() = client.getAllIdsProjects(MY_PUBLIC_KEY)
    suspend fun getUniqueObjectProject(id_parameter:Int) = client.getUniqueObjectProject(id_parameter,"2020-12-01",MY_PUBLIC_KEY)

}