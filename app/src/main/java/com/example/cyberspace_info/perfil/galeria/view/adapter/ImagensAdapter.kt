package com.example.cyberspace_info.perfil.galeria.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R

class ImagensAdapter(private val listaDeImagens: MutableList<String>):RecyclerView.Adapter<ImagensAdapter.ImagensViewHolder>() {

    class ImagensViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val imagem = view.findViewById<ImageView>(R.id.imageViewItem)

        fun bind(url:String){
            imagem.setImageResource(url.toInt())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagensViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_imagens, parent,false)

        return ImagensViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImagensViewHolder, position: Int) {
        holder.bind(listaDeImagens[position])
    }

    override fun getItemCount(): Int = listaDeImagens.size
}