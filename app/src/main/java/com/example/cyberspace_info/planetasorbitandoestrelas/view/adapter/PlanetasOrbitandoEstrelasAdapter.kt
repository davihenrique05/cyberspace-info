package com.example.cyberspace_info.planetasorbitandoestrelas.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.planetasorbitandoestrelas.model.PlanetaOrbitandoEstrelaModel

class PlanetasOrbitandoEstrelasAdapter(private val planetaModels: List<PlanetaOrbitandoEstrelaModel>): RecyclerView.Adapter<PlanetasOrbitandoEstrelasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetasOrbitandoEstrelasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_planetasorbitandoestrelas, parent, false)
        return PlanetasOrbitandoEstrelasViewHolder(
            view
        )
    }

    override fun getItemCount() = planetaModels.size

    override fun onBindViewHolder(holder: PlanetasOrbitandoEstrelasViewHolder, position: Int) {
        holder.bind(planetaModels[position])
    }
}