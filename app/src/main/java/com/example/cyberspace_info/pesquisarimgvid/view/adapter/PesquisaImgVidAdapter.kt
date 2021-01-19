package com.example.cyberspace_info.pesquisarimgvid.view.adapter

import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.pesquisarimgvid.model.LinksImageModel
import com.example.cyberspace_info.pesquisarimgvid.model.ObjectImageModel
import com.squareup.picasso.Picasso

class PesquisaImgVidAdapter(private val dataSet: List<ObjectImageModel>, private val listener: (ObjectImageModel) -> Unit): RecyclerView.Adapter<PesquisaImgVidAdapter.meuViewHolder>() {

    class meuViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val fotovideo: ImageView = view.findViewById(R.id.imgFotoVideo)

        fun bind(imgVid: ObjectImageModel){
            Picasso.get().load(imgVid.links[0].href).into(fotovideo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): meuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_fotosvideos, parent, false)
        return meuViewHolder(
            view
        )
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: meuViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }
}