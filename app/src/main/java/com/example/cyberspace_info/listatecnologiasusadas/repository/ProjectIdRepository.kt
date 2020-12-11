package com.example.cyberspace_info.listatecnologiasusadas.repository

import com.example.cyberspace_info.api.MY_PUBLIC_KEY

class ProjectIdRepository {

    val client = ProjectIdEndpoint.Endpoint

    suspend fun getAllIdsProjects() = client.getAllIdsProjects(MY_PUBLIC_KEY)
    suspend fun getUniqueObjectProject(id_parameter:Int) = client.getUniqueObjectProject(id_parameter,MY_PUBLIC_KEY)

}