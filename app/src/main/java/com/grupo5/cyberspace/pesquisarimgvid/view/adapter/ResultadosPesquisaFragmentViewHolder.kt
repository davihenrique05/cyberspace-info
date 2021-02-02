package com.grupo5.cyberspace.pesquisarimgvid.view.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.pesquisarimgvid.model.FotoVideoModel

class ResultadosPesquisaFragmentViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val fotoVideo: ImageView = view.findViewById(R.id.imgFotoVideo)


    fun bind(fotoouvideo: FotoVideoModel){
        fotoVideo.setImageResource(fotoouvideo.imagem)
    }
}