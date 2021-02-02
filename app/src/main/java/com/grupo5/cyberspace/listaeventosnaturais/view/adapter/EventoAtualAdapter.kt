package com.grupo5.cyberspace.listaeventosnaturais.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.listaeventosnaturais.model.EventNaturalModel
import com.squareup.picasso.Picasso

class EventoAtualAdapter(private val eventos: List<EventNaturalModel>): RecyclerView.Adapter<EventoAtualAdapter.EventoAtualViewHolder>() {

    class EventoAtualViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val title : TextView by lazy { view.findViewById<TextView>(R.id.txtTitleEvent)}
        private val category : TextView by lazy { view.findViewById<TextView>(R.id.txtCategoryEvent)}
        private val date : TextView by lazy { view.findViewById<TextView>(R.id.txtDateEvent)}
        private val imagemCordenadas : ImageView by lazy {view.findViewById<ImageView>(R.id.imgCoordinates)}

        fun bind(event:EventNaturalModel){


            val coordenadasUrl = "https://maps.googleapis.com/maps/api/staticmap?"+"center=${event.geometries[0].coordinates[1]},${event.geometries[0].coordinates[0]}&"+"zoom=11&size=250x250&key=AIzaSyDFf6J-wAKE1OS-K7EYkn_pbznUhy55J2w"

            Log.i("IMAGEM : ",coordenadasUrl)
            Picasso.get().load(coordenadasUrl).into(imagemCordenadas)

            title.text = event.title
            category.text = event.categories[0].title
            date.text = event.geometries[0].date.split("T")[0]
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoAtualViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_eventos_naturais, parent, false)
        return EventoAtualViewHolder(view)

    }

    override fun getItemCount(): Int = eventos.size
    override fun onBindViewHolder(holder: EventoAtualViewHolder, position: Int) {
        holder.bind(eventos[position])
    }

}