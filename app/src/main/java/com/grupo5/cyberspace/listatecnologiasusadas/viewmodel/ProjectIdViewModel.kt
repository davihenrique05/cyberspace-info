package com.grupo5.cyberspace.listatecnologiasusadas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.grupo5.cyberspace.listatecnologiasusadas.model.ProjectDataModel
import com.grupo5.cyberspace.listatecnologiasusadas.model.ProjectIdModel
import com.grupo5.cyberspace.listatecnologiasusadas.repository.ProjectIdRepository
import kotlinx.coroutines.Dispatchers

class ProjectIdViewModel(val repository: ProjectIdRepository) : ViewModel() {

    private var listaIdProjetos = mutableListOf<ProjectIdModel>()

    fun getAllIdsProjects() = liveData(Dispatchers.IO) {

        try {
            val response = repository.getAllIdsProjects()
            listaIdProjetos = response.projects.projects.toMutableList()
            emit(listaIdProjetos)
        } catch (ex: Exception) {
            emit(listaIdProjetos)
        }


    }

    fun getUniqueObjectProject(project: ProjectIdModel) = liveData(Dispatchers.IO) {

        val response = repository.getUniqueObjectProject(project.id)
        emit(response.project)
    }

    class ProjectIdViewModelFactory(private val repository: ProjectIdRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProjectIdViewModel(repository) as T
        }
    }
}