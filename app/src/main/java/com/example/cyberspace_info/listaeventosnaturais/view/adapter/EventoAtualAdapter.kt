package com.example.cyberspace_info.listaeventosnaturais.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listaeventosnaturais.model.TesteInfo

class EventoAtualAdapter(private val eventos: List<TesteInfo>, private val listener: (TesteInfo) -> Unit): RecyclerView.Adapter<EventoAtualAdapter.EventoAtualViewHolder>() {

    class EventoAtualViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val title : TextView by lazy { view.findViewById<TextView>(R.id.txtTitleEvent)}
        private val category : TextView by lazy { view.findViewById<TextView>(R.id.txtCategoryEvent)}
        private val date : TextView by lazy { view.findViewById<TextView>(R.id.txtDateEvent)}

        fun bind(event:TesteInfo){

            title.text = event.title
            category.text = event.category
            date.text = event.date

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoAtualViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_eventos_naturais, parent, false)

        return EventoAtualViewHolder(view)

    }

    override fun getItemCount(): Int = eventos.size

    override fun onBindViewHolder(holder: EventoAtualViewHolder, position: Int) {

        holder.bind(eventos[position])

        holder.itemView.setOnClickListener {
            Toast.makeText(it.context,"Teste",Toast.LENGTH_SHORT).show()
        }

    }

}