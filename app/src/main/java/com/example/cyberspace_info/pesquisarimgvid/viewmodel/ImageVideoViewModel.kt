package com.example.cyberspace_info.pesquisarimgvid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.cyberspace_info.pesquisarimgvid.repository.ImageVideoRepository
import kotlinx.coroutines.Dispatchers

class ImageVideoViewModel(val repository: ImageVideoRepository):ViewModel() {

    fun getUrlsImages(search:String) = liveData(Dispatchers.IO){

        try {
            val response = repository.getUrlsImages(search)
            emit(response.collection.items)
        }catch(ex:Exception){
            println(ex.message)
        }
    }


    class ImageVideoViewModelFactory(private val repository: ImageVideoRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ImageVideoViewModel(repository) as T
        }
    }

}