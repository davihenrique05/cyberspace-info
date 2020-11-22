package com.example.cyberspace_info.listaeventosnaturais.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listaeventosnaturais.model.CategoryEventModel
import com.example.cyberspace_info.listaeventosnaturais.model.EventNaturalModel
import com.example.cyberspace_info.listaeventosnaturais.model.GeometryEventModel
import com.example.cyberspace_info.listaeventosnaturais.model.TesteInfo
import com.example.cyberspace_info.listaeventosnaturais.view.adapter.EventoAtualAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class EventosNaturaisAtuaisFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eventos_naturais_atuais, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recyclerView = view.findViewById<RecyclerView>(R.id.listAtualEvents)
        var managerLinear = LinearLayoutManager(view.context)
        // criar o adapter
        recyclerView.apply {

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