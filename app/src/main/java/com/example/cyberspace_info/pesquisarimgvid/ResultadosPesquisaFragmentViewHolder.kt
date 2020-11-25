package com.example.cyberspace_info.pesquisarimgvid

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R

class ResultadosPesquisaFragmentViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val fotoVideo: ImageView = view.findViewById(R.id.imgFotoVideo)


    fun bind(fotoouvideo: FotoVideo){
        fotoVideo.setImageResource(fotoouvideo.imagem)
    }
}