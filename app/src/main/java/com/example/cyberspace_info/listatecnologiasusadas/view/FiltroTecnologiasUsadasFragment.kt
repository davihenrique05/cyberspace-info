package com.example.cyberspace_info.listatecnologiasusadas.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.cyberspace_info.R


class FiltroTecnologiasUsadasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filtro_tecnologias_usadas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnPesquisar = view.findViewById<Button>(R.id.btnFiltroTecnologiasUsadas)

        btnPesquisar.setOnClickListener {

            val objectId = view.findViewById<EditText>(R.id.edtObjectId).text.toString()
            val searchQuery = view.findViewById<EditText>(R.id.edtSearchQuery).text.toString()
            val missionDirectory = view.findViewById<EditText>(R.id.edtDirectoryMission).text.toString()

            val bundle = bundleOf("objectId" to objectId,"searchQuery" to searchQuery,"missionDirectory" to missionDirectory)

            val navigateController = Navigation.findNavController(view)
            navigateController.navigate(R.id.action_filtroTecnologiasUsadasFragment_to_resultsSearchTecnologiasUsadasFragment,bundle)

        }

    }


}