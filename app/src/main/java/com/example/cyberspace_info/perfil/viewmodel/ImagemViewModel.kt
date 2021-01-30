package com.example.cyberspace_info.perfil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.cyberspace_info.perfil.entity.ImagemEntity
import com.example.cyberspace_info.perfil.repository.ImagemRepository
import kotlinx.coroutines.Dispatchers

class ImagemViewModel(
    private val repository: ImagemRepository
) : ViewModel() {

    fun salvarImagem(url: String, uid: String) = liveData(Dispatchers.IO) {
        val imagem = ImagemEntity(0, url, uid)

        repository.salvarImagem(imagem)
        emit(imagem)
    }

    fun obterImagems(uid: String) = liveData(Dispatchers.IO) {
        val response = repository.obterImagems(uid)

        emit(response)
    }

    fun deletarImagem(url: String, uid: String) = liveData(Dispatchers.IO) {

        repository.deletarImagens(url, uid)
        emit(url)
    }

    class ImagemViewModelFacytory(private val repository: ImagemRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ImagemViewModel(repository) as T
        }
    }
}
