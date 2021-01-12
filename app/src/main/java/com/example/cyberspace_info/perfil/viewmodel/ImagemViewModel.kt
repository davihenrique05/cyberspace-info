package com.example.cyberspace_info.perfil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.cyberspace_info.perfil.entity.ImagemEntity
import com.example.cyberspace_info.perfil.repository.ImagemRepository
import kotlinx.coroutines.Dispatchers

class ImagemViewModel(
    private val repository: ImagemRepository
):ViewModel() {

    lateinit var _listadeimagens: MutableList<ImagemEntity>

    fun salvarImagem(url: String) = liveData(Dispatchers.IO){
        val imagem = ImagemEntity(0,url)

        repository.salvarImagem(imagem)
        emit(imagem)
    }

    fun obterImagems() = liveData(Dispatchers.IO) {
        val response = repository.obterImagems()
        
        emit(response)
    }

    fun deletarImagem(url: String) = liveData(Dispatchers.IO) {

        repository.deletarImagens(url)
        emit(url)
    }

    class ImagemViewModelFacytory(private val repository: ImagemRepository):
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ImagemViewModel(repository) as T
        }
    }
}
