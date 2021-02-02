package com.grupo5.cyberspace.listaeventosnaturais.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.listaeventosnaturais.model.EventNaturalModel
import com.grupo5.cyberspace.listaeventosnaturais.repository.EventosNaturaisRepository
import com.grupo5.cyberspace.listaeventosnaturais.view.adapter.EventoAtualAdapter
import com.grupo5.cyberspace.listaeventosnaturais.viewmodel.EventosNaturaisViewModel

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

        return inflater.inflate(R.layout.fragment_eventos_naturais_atuais, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progresBar = view.findViewById<ProgressBar>(R.id.progessBar)

        showLoading(true)
        val color = ContextCompat.getColor(view.context,R.color.colorWhite)
        @Suppress("DEPRECATION")
        progresBar.indeterminateDrawable.setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY)

        _adaptador = EventoAtualAdapter(_listaEventos)

        _viewModel = ViewModelProvider(this,EventosNaturaisViewModel.EventosNaturaisViewModelFactory(
            EventosNaturaisRepository()
        )).get(EventosNaturaisViewModel::class.java)

        _recyclerView = view.findViewById(R.id.listAtualEvents)
        val managerLinear = LinearLayoutManager(view.context)
        aplicationPropertyRecyclerView(managerLinear)

    }

    private fun showLoading(isLoading: Boolean) {

        val viewLoading = view?.findViewById<View>(R.id.loading)
        if (isLoading) {
            viewLoading?.visibility = View.VISIBLE
        } else {
            viewLoading?.visibility = View.GONE
        }
    }

    private fun aplicationPropertyRecyclerView(managerLinear:LinearLayoutManager){

        _recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = managerLinear
            adapter = _adaptador
        }

        _viewModel.getCurrentNaturalEvents().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                exibirResultado(it)
            }

        }

    }

    private fun exibirResultado(lista:List<EventNaturalModel>){
        _listaEventos.addAll(lista)
        showLoading(false)
        _adaptador.notifyDataSetChanged()
    }

}