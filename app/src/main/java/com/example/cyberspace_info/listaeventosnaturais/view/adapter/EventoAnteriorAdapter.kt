package com.example.cyberspace_info.listaeventosnaturais.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listaeventosnaturais.model.EventNaturalModel

class EventoAnteriorAdapter(private val eventos: List<EventNaturalModel>) : RecyclerView.Adapter<EventoAnteriorAdapter.EventoAnteriorViewHolder>() {

    class EventoAnteriorViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val title : TextView by lazy { view.findViewById<TextView>(R.id.txtTitleEvent)}
        private val category : TextView by lazy { view.findViewById<TextView>(R.id.txtCategoryEvent)}
        private val date : TextView by lazy { view.findViewById<TextView>(R.id.txtDateEvent)}

        fun bind(event:EventNaturalModel){

            title.text = event.title
            category.text = event.categories[0].title
            date.text = event.geometries[0].date.split("T")[0]
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoAnteriorViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_eventos_naturais,parent,false)

        return EventoAnteriorViewHolder(view)

    }

    override fun getItemCount(): Int = eventos.size

    override fun onBindViewHolder(holder: EventoAnteriorViewHolder, position: Int) {
        val item = eventos[position]
        holder.bind(item)

    }
}