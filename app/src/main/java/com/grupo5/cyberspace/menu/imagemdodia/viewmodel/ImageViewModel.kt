package com.grupo5.cyberspace.menu.imagemdodia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.grupo5.cyberspace.menu.imagemdodia.repository.ImageModelRepository
import kotlinx.coroutines.Dispatchers

class ImageViewModel(
    private val repository: ImageModelRepository
) : ViewModel() {

    fun obterImagemDoDia() = liveData(Dispatchers.IO) {
        val response = repository.obterImagemDoDia()
        emit(response)
    }

    class ImageViewModelFactory(private val repository: ImageModelRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ImageViewModel(repository) as T
        }
    }
}