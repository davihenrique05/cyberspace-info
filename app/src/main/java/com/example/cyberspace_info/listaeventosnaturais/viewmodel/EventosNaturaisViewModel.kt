package com.example.cyberspace_info.listaeventosnaturais.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.cyberspace_info.listaeventosnaturais.repository.EventosNaturaisRepository
import kotlinx.coroutines.Dispatchers

class EventosNaturaisViewModel(private var repository: EventosNaturaisRepository) : ViewModel() {

     fun getPastNaturalEvents() = liveData(Dispatchers.IO) {

         val numberDays = 40
         val numberEvents = 40
         val status = "open"

         try {
             val response = repository.getListEvents(numberDays,numberEvents,status)
             response.events = response.events.slice(19 until response.events.size)
             emit(response.events)
         }catch(ex:Exception){
             Log.e("ERRO_EVENTO_PASSADO",ex.message.toString())
         }

    }

    fun getCurrentNaturalEvents() = liveData(Dispatchers.IO) {

        val numberDays = 20
        val numberEvents = 20
        val status = "open"

        try {

            val response = repository.getListEvents(numberDays,numberEvents,status)
            response.events = response.events.slice(0..(response.events.size-1))
            emit(response.events)

        }catch(ex:Exception){
            //println("Erro:"+ex.message)
            Log.e("ERRO_EVENTO_ATUAL",ex.message.toString())
        }

    }

    class EventosNaturaisViewModelFactory(private val repository: EventosNaturaisRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EventosNaturaisViewModel(repository) as T
        }
    }

}