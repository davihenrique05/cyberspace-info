package com.example.cyberspace_info.asteroidesemcolisao.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.asteroidesemcolisao.model.AsteroideModel

class AsteroidesAdapter(private val listaDeImagens: MutableList<AsteroideModel>, private val listener: (AsteroideModel) -> Unit):RecyclerView.Adapter<AsteroidesAdapter.ImagensViewHolder>() {

    class ImagensViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val nome = view.findViewById<TextView>(R.id.txtTitleAsteroid)
        private val  diametroMax = view.findViewById<TextView>(R.id.txtDiametroAsteroides)
        private val velocidade = view.findViewById<TextView>(R.id.txtVelocidadeAsteroides)
        private val data =  view.findViewById<TextView>(R.id.txtDataAsteroides)

        @SuppressLint("SetTextI18n")
        fun bind(asteroideModel:AsteroideModel){
            nome.text = asteroideModel.nome
            diametroMax.text = "Diametro: ${asteroideModel.diametroMaximo}"
            velocidade.text = "Velocidade: ${asteroideModel.velocidadeModelRel}"
            data.text = asteroideModel.data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagensViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_asteroides, parent,false)

        return ImagensViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImagensViewHolder, position: Int) {
        val item = listaDeImagens[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    override fun getItemCount(): Int = listaDeImagens.size
}