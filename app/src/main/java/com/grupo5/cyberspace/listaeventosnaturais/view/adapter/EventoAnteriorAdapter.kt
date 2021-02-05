package com.grupo5.cyberspace.listaeventosnaturais.view.adapter

import android.annotation.SuppressLint
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
import java.text.SimpleDateFormat

class EventoAnteriorAdapter(private val eventos: List<EventNaturalModel>,  private val listener: (EventNaturalModel) -> Unit) :
    RecyclerView.Adapter<EventoAnteriorAdapter.EventoAnteriorViewHolder>() {

    class EventoAnteriorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title: TextView by lazy { view.findViewById<TextView>(R.id.txtTitleEvent) }
        private val category: TextView by lazy { view.findViewById<TextView>(R.id.txtCategoryEvent) }
        private val date: TextView by lazy { view.findViewById<TextView>(R.id.txtDateEvent) }
        private val imagemCordenadas: ImageView by lazy { view.findViewById<ImageView>(R.id.imgCoordinates) }

        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        @SuppressLint("SimpleDateFormat")
        fun bind(event: EventNaturalModel) {

            val coordenadasUrl =
                "https://maps.googleapis.com/maps/api/staticmap?" + "center=${event.geometries[0].coordinates[1]},${event.geometries[0].coordinates[0]}&" + "zoom=11&size=250x250&key=AIzaSyDFf6J-wAKE1OS-K7EYkn_pbznUhy55J2w"

            Log.i("IMAGEM : ", coordenadasUrl)
            Picasso.get().load(coordenadasUrl).into(imagemCordenadas)

            val data = event.geometries[0].date.split("T")[0]

            title.text = event.title
            category.text = event.categories[0].title
            date.text = formatarData(data)
        }

        @SuppressLint("SimpleDateFormat")
        fun formatarData(data:String): String {
            val formato = SimpleDateFormat("yyyy-MM-dd")
            val dataUm = formato.parse(data)
            formato.applyPattern("dd-MM-yyyy")
            return formato.format(dataUm)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoAnteriorViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_eventos_naturais, parent, false)
        return EventoAnteriorViewHolder(view)

    }

    override fun getItemCount(): Int = eventos.size

    override fun onBindViewHolder(holder: EventoAnteriorViewHolder, position: Int) {
        val item = eventos[position]
        holder.bind(item)
        holder.itemView.findViewById<ImageView>(R.id.imgCoordinates).setOnClickListener {
            listener(item)
        }

    }
}