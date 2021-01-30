package com.example.cyberspace_info.pesquisarimgvid.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.pesquisarimgvid.model.ObjectImageModel
import com.squareup.picasso.Picasso
import java.lang.Exception

class PesquisaImgAdapter(private val dataSet: List<ObjectImageModel>, private val listener: (ObjectImageModel) -> Unit): RecyclerView.Adapter<PesquisaImgAdapter.MeuViewHolder>() {

    class MeuViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val fotovideo: ImageView = view.findViewById(R.id.imgFotoVideo)

        fun bind(imgVid: ObjectImageModel){
            try{
                Picasso.get().load(imgVid.links[0].href).into(fotovideo)
            }catch (e: Exception){
                Log.e("Leitura",e.message.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_fotosvideos, parent, false)
        return MeuViewHolder(
            view
        )
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: MeuViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }
}