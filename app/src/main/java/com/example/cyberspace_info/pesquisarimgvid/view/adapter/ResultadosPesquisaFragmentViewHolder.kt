package com.example.cyberspace_info.pesquisarimgvid.view.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.pesquisarimgvid.model.FotoVideoModel

class ResultadosPesquisaFragmentViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val fotoVideo: ImageView = view.findViewById(R.id.imgFotoVideo)


    fun bind(fotoouvideo: FotoVideoModel){
        fotoVideo.setImageResource(fotoouvideo.imagem)
    }
}