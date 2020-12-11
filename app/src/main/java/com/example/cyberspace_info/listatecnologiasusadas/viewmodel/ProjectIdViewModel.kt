package com.example.cyberspace_info.listatecnologiasusadas.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.cyberspace_info.listaeventosnaturais.repository.EventosNaturaisRepository
import com.example.cyberspace_info.listaeventosnaturais.viewmodel.EventosNaturaisViewModel
import com.example.cyberspace_info.listatecnologiasusadas.model.ProjectDataModel
import com.example.cyberspace_info.listatecnologiasusadas.model.ProjectIdModel
import com.example.cyberspace_info.listatecnologiasusadas.repository.ProjectIdRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ProjectIdViewModel(val repository: ProjectIdRepository):ViewModel(){

    private var listaTecnologias = mutableListOf<ProjectDataModel>()

    fun getAllIdsProjects() = liveData(Dispatchers.IO) {

        val response = repository.getAllIdsProjects()
        emit(response.projects.projects)

    }

     fun getUniqueObjectProject(listProjeto: List<ProjectIdModel>) = liveData(Dispatchers.IO) {

                for(i in 0..9) {
                    val response = repository.getUniqueObjectProject(listProjeto[i].id)
                    listaTecnologias.add(response.project)
                }
                emit(listaTecnologias)

     }

    class ProjectIdViewModelFactory(private val repository: ProjectIdRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProjectIdViewModel(repository) as T
        }
    }
}