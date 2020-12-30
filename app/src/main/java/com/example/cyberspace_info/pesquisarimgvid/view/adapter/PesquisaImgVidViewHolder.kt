package com.example.cyberspace_info.pesquisarimgvid.view.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.planetasorbitandoestrelas.model.PlanetaOrbitandoEstrelaModel

class PesquisaImgVidViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val nomePlaneta: TextView = view.findViewById(R.id.txtNomePlaneta)
    private val nomeEstrela: TextView = view.findViewById(R.id.txtNomeEstrela)


    @SuppressLint("SetTextI18n")
    fun bind(planetaModel: PlanetaOrbitandoEstrelaModel){
        nomePlaneta.text = "Nome do planeta: " + planetaModel.nomePlaneta
        nomeEstrela.text = "Nome da Estrela: " + planetaModel.nomeEstela
    }
}