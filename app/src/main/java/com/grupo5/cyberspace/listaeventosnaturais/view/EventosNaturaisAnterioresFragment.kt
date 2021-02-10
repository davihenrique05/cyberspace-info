package com.grupo5.cyberspace.listaeventosnaturais.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.listaeventosnaturais.model.EventNaturalModel
import com.grupo5.cyberspace.listaeventosnaturais.repository.EventosNaturaisRepository
import com.grupo5.cyberspace.listaeventosnaturais.view.adapter.EventoAnteriorAdapter
import com.grupo5.cyberspace.listaeventosnaturais.viewmodel.EventosNaturaisViewModel

class EventosNaturaisAnterioresFragment : Fragment() {

    private lateinit var _recyclerView:RecyclerView
    private lateinit var _adaptador:EventoAnteriorAdapter
    private lateinit var _viewModel : EventosNaturaisViewModel
    private lateinit var _view : View
    private lateinit var listaEventos : MutableList<EventNaturalModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        listaEventos = mutableListOf()

        val view =  inflater.inflate(R.layout.fragment_eventos_naturais_anteriores, container, false)
        _view = view
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progresBar = view.findViewById<ProgressBar>(R.id.progessBar)

        showLoading(true)
        val color = ContextCompat.getColor(view.context,R.color.colorWhite)
        @Suppress("DEPRECATION")
        progresBar.indeterminateDrawable.setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY)

        _recyclerView = view.findViewById(R.id.listAnterioresEvents)

        _viewModel = ViewModelProvider(this,EventosNaturaisViewModel.EventosNaturaisViewModelFactory(
            EventosNaturaisRepository()
        )).get(EventosNaturaisViewModel::class.java)

        _adaptador = EventoAnteriorAdapter(listaEventos){
            showImageEvent(it.geometries[0].coordinates[0].toString(),it.geometries[0].coordinates[1].toString())
        }

        _viewModel.getPastNaturalEvents().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                exibirResultado(it)
            }
        }

        val managerLinear = LinearLayoutManager(view.context)
        aplicationPropertyRecyclerView(managerLinear)

    }

    private fun showImageEvent(latitude:String,longitude:String){
        val bundle = bundleOf("latitude" to latitude, "longitude" to longitude)
        val navController = Navigation.findNavController(_view)
        navController.navigate(R.id.action_eventosNaturaisFragment_to_imagemEventosNaturaisFragment,bundle)
    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = view?.findViewById<View>(R.id.loading)

        if (isLoading) {
            viewLoading?.visibility = View.VISIBLE
        } else {
            viewLoading?.visibility = View.GONE
        }
    }

    private fun exibirResultado(lista:List<EventNaturalModel>){
        listaEventos.clear()
        listaEventos.addAll(lista)
        showLoading(false)
        _adaptador.notifyDataSetChanged()

    }

    private fun aplicationPropertyRecyclerView(managerLinear:LinearLayoutManager){

        _recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = managerLinear
            adapter = _adaptador

        }

    }

}