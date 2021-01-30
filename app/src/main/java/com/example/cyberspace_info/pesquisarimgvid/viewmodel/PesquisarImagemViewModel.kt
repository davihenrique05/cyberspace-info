package com.example.cyberspace_info.pesquisarimgvid.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.cyberspace_info.pesquisarimgvid.model.ObjectImageModel
import com.example.cyberspace_info.pesquisarimgvid.repository.PesquisarImagemRepository
import kotlinx.coroutines.Dispatchers

class PesquisarImagemViewModel(val repository: PesquisarImagemRepository):ViewModel() {
    private var _lista = mutableListOf<ObjectImageModel>()
    fun getUrlsImages(search:String) = liveData(Dispatchers.IO){
        try {
            val response = repository.getUrlsImages(search)
            _lista = response.collection.items.toMutableList()
            emit(_lista)
        }catch(ex:Exception){
            Log.e("Requisicao", ex.stackTrace.toString())
            emit(_lista)
        }
    }


    class PesquisarImagemViewModelFactory(private val repository: PesquisarImagemRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PesquisarImagemViewModel(repository) as T
        }
    }

}