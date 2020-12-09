package com.example.cyberspace_info.listaeventosnaturais.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listaeventosnaturais.model.CategoryEventModel
import com.example.cyberspace_info.listaeventosnaturais.model.EventNaturalModel
import com.example.cyberspace_info.listaeventosnaturais.model.GeometryEventModel
import com.example.cyberspace_info.listaeventosnaturais.repository.EventosNaturaisRepository
import com.example.cyberspace_info.listaeventosnaturais.view.adapter.EventoAtualAdapter
import com.example.cyberspace_info.listaeventosnaturais.viewmodel.EventosNaturaisViewModel

class EventosNaturaisAtuaisFragment : Fragment() {

    private lateinit var _recyclerView : RecyclerView
    private lateinit var _adaptador : EventoAtualAdapter
    private lateinit var _viewModel : EventosNaturaisViewModel
    private lateinit var _listaEventos : MutableList<EventNaturalModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _listaEventos = mutableListOf()
        var view =  inflater.inflate(R.layout.fragment_eventos_naturais_atuais, container, false)

        _adaptador = EventoAtualAdapter(_listaEventos)

        _viewModel = ViewModelProvider(this,EventosNaturaisViewModel.EventosNaturaisViewModelFactory(
            EventosNaturaisRepository()
        )).get(EventosNaturaisViewModel::class.java)

        _recyclerView = view.findViewById<RecyclerView>(R.id.listAtualEvents)
        var managerLinear = LinearLayoutManager(view.context)
        aplicationPropertyRecyclerView(managerLinear)

        return view
    }

    fun aplicationPropertyRecyclerView(managerLinear:LinearLayoutManager){

        _recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = managerLinear
            adapter = _adaptador
        }

        _viewModel.getCurrentNaturalEvents().observe(viewLifecycleOwner,{
               exibirResultado(it)
        })

    }

    fun exibirResultado(lista:List<EventNaturalModel>){
        _listaEventos.addAll(lista)
        _adaptador.notifyDataSetChanged()
    }

}