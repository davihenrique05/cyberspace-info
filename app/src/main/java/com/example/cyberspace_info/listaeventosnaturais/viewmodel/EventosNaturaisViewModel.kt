package com.example.cyberspace_info.listaeventosnaturais.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.cyberspace_info.listaeventosnaturais.model.EventNaturalModel
import com.example.cyberspace_info.listaeventosnaturais.repository.EventosNaturaisRepository
import kotlinx.coroutines.Dispatchers

class EventosNaturaisViewModel(private var repository: EventosNaturaisRepository) : ViewModel() {

    private var _listaAtual = mutableListOf<EventNaturalModel>()
    private var _listaAntigos = mutableListOf<EventNaturalModel>()
     fun getPastNaturalEvents() = liveData(Dispatchers.IO) {

         val numberDays = 40
         val numberEvents = 40
         val status = "open"

         try {
             val response = repository.getListEvents(numberDays,numberEvents,status)
             _listaAtual = response.events.slice(19 until response.events.size).toMutableList()
             emit(_listaAtual)
         }catch(ex:Exception){
             Log.e("ERRO_EVENTO_PASSADO",ex.message.toString())
             emit(_listaAtual)
         }
    }

    fun getCurrentNaturalEvents() = liveData(Dispatchers.IO) {

        val numberDays = 20
        val numberEvents = 20
        val status = "open"

        try {

            val response = repository.getListEvents(numberDays,numberEvents,status)
            _listaAntigos = response.events.slice(response.events.indices).toMutableList()
            emit(_listaAntigos)
        }catch(ex:Exception){
            Log.e("ERRO_EVENTO_ATUAL",ex.message.toString())
            emit(_listaAntigos)
        }

    }

    class EventosNaturaisViewModelFactory(private val repository: EventosNaturaisRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EventosNaturaisViewModel(repository) as T
        }
    }

}