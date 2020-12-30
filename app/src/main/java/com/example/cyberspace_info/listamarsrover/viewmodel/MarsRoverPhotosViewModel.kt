package com.example.cyberspace_info.listamarsrover.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.cyberspace_info.listamarsrover.model.MarsRoverPhotosModel
import com.example.cyberspace_info.listamarsrover.repository.MarsRoverPhotosRepository
import kotlinx.coroutines.Dispatchers

class MarsRoverPhotosViewModel(
    private val repository: MarsRoverPhotosRepository
) : ViewModel() {

    private var photos: List<MarsRoverPhotosModel> = listOf()

    fun obterLista(rover: String, earthDate: String, page: Int = 1) = liveData(Dispatchers.IO) {
        val response = repository.obterRover(rover, earthDate, page)
        photos = response.fotos
        emit(response.fotos)
    }

    class MarsRoverPhotosViewModelFactory(private val repository: MarsRoverPhotosRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MarsRoverPhotosViewModel(repository) as T
        }
    }
}