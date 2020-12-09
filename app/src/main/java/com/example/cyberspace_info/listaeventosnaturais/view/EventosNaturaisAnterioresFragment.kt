package com.example.cyberspace_info.listaeventosnaturais.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listaeventosnaturais.model.CategoryEventModel
import com.example.cyberspace_info.listaeventosnaturais.model.EventNaturalModel
import com.example.cyberspace_info.listaeventosnaturais.model.GeometryEventModel
import com.example.cyberspace_info.listaeventosnaturais.repository.EventosNaturaisRepository
import com.example.cyberspace_info.listaeventosnaturais.view.adapter.EventoAnteriorAdapter
import com.example.cyberspace_info.listaeventosnaturais.viewmodel.EventosNaturaisViewModel

class EventosNaturaisAnterioresFragment : Fragment() {

    private lateinit var _recyclerView:RecyclerView
    private lateinit var _adaptador:EventoAnteriorAdapter
    private lateinit var _viewModel : EventosNaturaisViewModel

    private lateinit var listaEventos : MutableList<EventNaturalModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        listaEventos = mutableListOf()

       var view =  inflater.inflate(R.layout.fragment_eventos_naturais_anteriores, container, false)

        _recyclerView = view.findViewById<RecyclerView>(R.id.listAnterioresEvents)

        _viewModel = ViewModelProvider(this,EventosNaturaisViewModel.EventosNaturaisViewModelFactory(
            EventosNaturaisRepository()
        )).get(EventosNaturaisViewModel::class.java)

        _adaptador = EventoAnteriorAdapter(listaEventos)

        _viewModel.getPastNaturalEvents().observe(viewLifecycleOwner,{
            exibirResultado(it)
        })

        var managerLinear = LinearLayoutManager(view.context)
        aplicationPropertyRecyclerView(managerLinear)
        return view
    }

    fun exibirResultado(lista:List<EventNaturalModel>){
        listaEventos.addAll(lista)
        _adaptador.notifyDataSetChanged()
    }

    fun aplicationPropertyRecyclerView(managerLinear:LinearLayoutManager){

        _recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = managerLinear
            adapter = _adaptador

        }

    }

}