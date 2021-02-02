package com.grupo5.cyberspace.pesquisarimgvid.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.pesquisarimgvid.model.FotoVideoModel

class ResultadosPesquisaFragmentAdpater(private val dataSet: List<FotoVideoModel>): RecyclerView.Adapter<ResultadosPesquisaFragmentAdpater.MeuViewHolder>() {
    //Para tratar cada elemento da linha, colocar todos os elementos aqui
    class MeuViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val fotoPlaneta: ImageView = view.findViewById(R.id.imgFotoVideo)

        fun bind(fotoVideoModel: FotoVideoModel){
            fotoPlaneta.setImageResource(fotoVideoModel.imagem)
        }
    }

    //Usar xml como item de linha
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_fotosvideos, parent, false)
        return MeuViewHolder(
            view
        )
    }

    //Quantidade de itens
    override fun getItemCount() = dataSet.size

    //Quando chegar elemento novo, chamar para cada elemento
    override fun onBindViewHolder(holder: MeuViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }
}