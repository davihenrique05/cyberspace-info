package com.example.cyberspace_info.listatecnologiasusadas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.cyberspace_info.listatecnologiasusadas.model.ProjectDataModel
import com.example.cyberspace_info.listatecnologiasusadas.model.ProjectIdModel
import com.example.cyberspace_info.listatecnologiasusadas.repository.ProjectIdRepository
import kotlinx.coroutines.Dispatchers

class ProjectIdViewModel(val repository: ProjectIdRepository) : ViewModel() {

    private var listaTecnologias = mutableListOf<ProjectDataModel>()
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
        listaTecnologias.add(response.project)
        emit(listaTecnologias)

    }

    class ProjectIdViewModelFactory(private val repository: ProjectIdRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProjectIdViewModel(repository) as T
        }
    }
}