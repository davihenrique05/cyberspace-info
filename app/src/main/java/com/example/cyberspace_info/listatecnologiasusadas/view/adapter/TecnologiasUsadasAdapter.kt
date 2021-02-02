package com.example.cyberspace_info.listatecnologiasusadas.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listatecnologiasusadas.model.ProjectDataModel


class TecnologiasUsadasAdapter(
    private val tecnologias: List<ProjectDataModel>,
    val listener: (ProjectDataModel) -> Unit
) : RecyclerView.Adapter<TecnologiasUsadasAdapter.TecnologiasUsadasViewHolder>() {

    class TecnologiasUsadasViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title: TextView by lazy { view.findViewById<TextView>(R.id.txtTitleTecnologiaUsada) }
        private val date: TextView by lazy { view.findViewById<TextView>(R.id.txtDataTecnologiaUsada) }
        private val status: TextView by lazy { view.findViewById<TextView>(R.id.txtStatusTecnologiaUsada) }

        fun bind(tecnologia: ProjectDataModel) {

            title.text = tecnologia.title
            date.text = "${tecnologia.startDate} - ${tecnologia.endDate}"
            status.text = tecnologia.status

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TecnologiasUsadasViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lista_tecnologias_usadas, parent, false)

        return TecnologiasUsadasViewHolder(view)

    }

    override fun getItemCount(): Int = tecnologias.size

    override fun onBindViewHolder(holder: TecnologiasUsadasViewHolder, position: Int) {

        holder.bind(tecnologias[position])


        holder.itemView.setOnClickListener {
            listener(tecnologias[position])
        }

    }

}