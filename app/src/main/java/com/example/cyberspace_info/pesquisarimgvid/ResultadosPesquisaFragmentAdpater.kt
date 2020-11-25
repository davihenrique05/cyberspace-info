package com.example.cyberspace_info.pesquisarimgvid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R

class ResultadosPesquisaFragmentAdpater(private val dataSet: List<FotoVideo>): RecyclerView.Adapter<ResultadosPesquisaFragmentAdpater.meuViewHolder>() {
    //Para tratar cada elemento da linha, colocar todos os elementos aqui
    class meuViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val fotoPlaneta: ImageView = view.findViewById(R.id.imgFotoVideo)

        fun bind(fotoVideo: FotoVideo){
            fotoPlaneta.setImageResource(fotoVideo.imagem)
        }
    }

    //Usar xml como item de linha
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): meuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_fotosvideos, parent, false)
        return meuViewHolder(view)
    }

    //Quantidade de itens
    override fun getItemCount() = dataSet.size

    //Quando chegar elemento novo, chamar para cada elemento
    override fun onBindViewHolder(holder: meuViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }
}