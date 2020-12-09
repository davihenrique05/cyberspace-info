package com.example.cyberspace_info.listaeventosnaturais.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listaeventosnaturais.model.CategoryEventModel
import com.example.cyberspace_info.listaeventosnaturais.model.EventNaturalModel
import com.example.cyberspace_info.listaeventosnaturais.model.GeometryEventModel
import com.example.cyberspace_info.listaeventosnaturais.view.adapter.EventoAtualAdapter

class EventosNaturaisAtuaisFragment : Fragment() {

    private lateinit var _recyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view =  inflater.inflate(R.layout.fragment_eventos_naturais_atuais, container, false)

        _recyclerView = view.findViewById<RecyclerView>(R.id.listAtualEvents)
        var managerLinear = LinearLayoutManager(view.context)

        aplicationPropertyRecyclerView(managerLinear)

        return view

    }


    fun aplicationPropertyRecyclerView(managerLinear:LinearLayoutManager){

        _recyclerView.apply {

            setHasFixedSize(true)
            layoutManager = managerLinear
            adapter = EventoAtualAdapter(

                listOf(

                    EventNaturalModel("Wildfire - Pinto Commune (Reserva Nuble Fire)",
                        CategoryEventModel(1,"Categoria : Incendios Florestais"), GeometryEventModel("20/10/2020",
                            arrayOf(1,2))
                    ),

                    EventNaturalModel("Wildfire - Pinto Commune (Reserva Nuble Fire)",
                        CategoryEventModel(1,"Categoria : Incendios Florestais"), GeometryEventModel("20/10/2020",
                            arrayOf(1,2))
                    ),

                    EventNaturalModel("Wildfire - Pinto Commune (Reserva Nuble Fire)",
                        CategoryEventModel(1,"Categoria : Incendios Florestais"), GeometryEventModel("20/10/2020",
                            arrayOf(1,2))
                    ),

                    EventNaturalModel("Wildfire - Pinto Commune (Reserva Nuble Fire)",
                        CategoryEventModel(1,"Categoria : Incendios Florestais"), GeometryEventModel("20/10/2020",
                            arrayOf(1,2))
                    ),

                    EventNaturalModel("Wildfire - Pinto Commune (Reserva Nuble Fire)",
                        CategoryEventModel(1,"Categoria : Incendios Florestais"), GeometryEventModel("20/10/2020",
                            arrayOf(1,2))
                    )

                )

            )

        }


    }

}