package com.example.cyberspace_info.listaeventosnaturais.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listaeventosnaturais.model.TesteInfo
import com.google.android.material.bottomsheet.BottomSheetDialog

class EventoAnteriorAdapter(private val eventos: List<TesteInfo>, private val listener:(TesteInfo) -> Unit) : RecyclerView.Adapter<EventoAnteriorAdapter.EventoAnteriorViewHolder>() {

    class EventoAnteriorViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val title : TextView by lazy { view.findViewById<TextView>(R.id.txtTitleEvent)}
        private val category : TextView by lazy { view.findViewById<TextView>(R.id.txtCategoryEvent)}
        private val date : TextView by lazy { view.findViewById<TextView>(R.id.txtDateEvent)}

        fun bind(event:TesteInfo){
            title.text = event.title
            category.text = event.category
            date.text = event.date
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoAnteriorAdapter.EventoAnteriorViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_eventos_naturais,parent,false)

        return EventoAnteriorViewHolder(view)

    }

    override fun getItemCount(): Int = eventos.size

    override fun onBindViewHolder(holder: EventoAnteriorAdapter.EventoAnteriorViewHolder, position: Int) {
        val item = eventos[position]
        holder.bind(item)



        holder.itemView.setOnClickListener {
            listener(item)
        }
    }
}