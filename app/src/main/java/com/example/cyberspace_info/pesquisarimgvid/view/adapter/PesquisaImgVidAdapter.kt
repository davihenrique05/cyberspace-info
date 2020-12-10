package com.example.cyberspace_info.pesquisarimgvid.view.adapter

import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R

class PesquisaImgVidAdapter(private val dataSet: List<Icon>): RecyclerView.Adapter<PesquisaImgVidAdapter.meuViewHolder>() {
    //Para tratar cada elemento da linha, colocar todos os elementos aqui
    class meuViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val fotovideo: ImageView = view.findViewById(R.id.imgFotoVideo)

        fun bind(imgVid: Icon){
            //fotovideo.drawable() = imgVid
        }
    }

    //Usar xml como item de linha
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): meuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_fotosvideos, parent, false)
        return meuViewHolder(
            view
        )
    }

    //Quantidade de itens
    override fun getItemCount() = dataSet.size

    //Quando chegar elemento novo, chamar para cada elemento
    override fun onBindViewHolder(holder: meuViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }
}