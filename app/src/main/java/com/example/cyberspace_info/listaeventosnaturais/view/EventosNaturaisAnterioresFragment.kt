package com.example.cyberspace_info.listaeventosnaturais.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listaeventosnaturais.model.EventNaturalModel
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

        val view =  inflater.inflate(R.layout.fragment_eventos_naturais_anteriores, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progresBar = view.findViewById<ProgressBar>(R.id.progessBar)

        showLoading(true)
        val color = ContextCompat.getColor(view.context,R.color.colorPrimaryDarkMenu)
        @Suppress("DEPRECATION")
        progresBar.indeterminateDrawable.setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY)

        _recyclerView = view.findViewById<RecyclerView>(R.id.listAnterioresEvents)

        _viewModel = ViewModelProvider(this,EventosNaturaisViewModel.EventosNaturaisViewModelFactory(
            EventosNaturaisRepository()
        )).get(EventosNaturaisViewModel::class.java)

        _adaptador = EventoAnteriorAdapter(listaEventos)

        _viewModel.getPastNaturalEvents().observe(viewLifecycleOwner,{
            if(!it.isNullOrEmpty()){
                exibirResultado(it)
            }
        })

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

    fun exibirResultado(lista:List<EventNaturalModel>){

        listaEventos.addAll(lista)
        showLoading(false)
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