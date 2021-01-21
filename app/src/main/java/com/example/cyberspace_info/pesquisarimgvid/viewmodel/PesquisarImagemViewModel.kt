package com.example.cyberspace_info.pesquisarimgvid.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.cyberspace_info.pesquisarimgvid.repository.PesquisarImagemRepository
import kotlinx.coroutines.Dispatchers

class PesquisarImagemViewModel(val repository: PesquisarImagemRepository):ViewModel() {

    fun getUrlsImages(search:String) = liveData(Dispatchers.IO){

        try {
            var response = repository.getUrlsImages(search)
            emit(response.collection.items)
        }catch(ex:Exception){
            Log.e("Requisição", ex.message.toString())
        }
    }


    class PesquisarImagemViewModelFactory(private val repository: PesquisarImagemRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PesquisarImagemViewModel(repository) as T
        }
    }

}