package com.example.cyberspace_info.planetasorbitandoestrelas

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.squareup.picasso.Picasso

class PlanetasOrbitandoEstrelasViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val nomePlaneta: TextView = view.findViewById(R.id.txtNomePlaneta)
    private val nomeEstrela: TextView = view.findViewById(R.id.txtNomeEstrela)
    private val fotoPlaneta: ImageView = view.findViewById(R.id.imgPlanetaOrbitandoEstrela)


    fun bind(planeta: PlanetaOrbitandoEstrela){
        nomePlaneta.text = planeta.nomePlaneta
        nomeEstrela.text = "Nome da Estrela: " + planeta.nomeEstela
        fotoPlaneta.setImageResource(planeta.imagem)
    }
}