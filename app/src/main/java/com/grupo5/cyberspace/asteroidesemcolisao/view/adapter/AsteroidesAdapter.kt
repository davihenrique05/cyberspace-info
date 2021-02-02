package com.grupo5.cyberspace.asteroidesemcolisao.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.asteroidesemcolisao.model.AsteroideModel
import java.text.SimpleDateFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AsteroidesAdapter(
    private val listaDeImagens: MutableList<AsteroideModel>,
    private val listener: (AsteroideModel) -> Unit
):RecyclerView.Adapter<AsteroidesAdapter.ImagensViewHolder>() {

    class ImagensViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val nome = view.findViewById<TextView>(R.id.txtTitleAsteroid)
        private val  diametroMax = view.findViewById<TextView>(R.id.txtDiametroAsteroides)
        private val velocidade = view.findViewById<TextView>(R.id.txtVelocidadeAsteroides)
        private val data =  view.findViewById<TextView>(R.id.txtDataAsteroides)



        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(asteroideModel: AsteroideModel){
            val diametroFormatado = String.format("%.2f", asteroideModel.diametroMaximo).toDouble()
            val velocidadeFormatada = String.format("%.2f", asteroideModel.velocidadeModelRel).toDouble()
            val dataFormatada = formatarData(asteroideModel.data)


            nome.text = asteroideModel.nome
            diametroMax.text = "Diametro: $diametroFormatado KM"
            velocidade.text = "Velocidade: $velocidadeFormatada Km/h"
            data.text = dataFormatada

        }

        @SuppressLint("SimpleDateFormat")
        fun formatarData(data:String): String {
            val formato = SimpleDateFormat("yyyy-MM-dd")
            val dataUm = formato.parse(data)
            formato.applyPattern("dd-MM-yyyy")
            return formato.format(dataUm)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagensViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_lista_asteroides,
            parent,
            false
        )

        return ImagensViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImagensViewHolder, position: Int) {
        val item = listaDeImagens[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    override fun getItemCount(): Int = listaDeImagens.size

}