package com.example.cyberspace_info.listaeventosnaturais.view.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listaeventosnaturais.model.TesteInfo

class EventoAtualAdapter(private val eventos: List<TesteInfo>): RecyclerView.Adapter<EventoAtualAdapter.EventoAtualViewHolder>() {

    class EventoAtualViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val title : TextView by lazy { view.findViewById<TextView>(R.id.txtTitleEvent)}
        private val category : TextView by lazy { view.findViewById<TextView>(R.id.txtCategoryEvent)}
        private val date : TextView by lazy { view.findViewById<TextView>(R.id.txtDateEvent)}

        fun bind(event:TesteInfo){

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoAtualViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = eventos.size

    override fun onBindViewHolder(holder: EventoAtualViewHolder, position: Int) {

        holder.bind(eventos[position])

    }

}