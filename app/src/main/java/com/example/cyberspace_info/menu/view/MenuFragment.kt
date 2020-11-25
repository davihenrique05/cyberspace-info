package com.example.cyberspace_info.menu.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.example.cyberspace_info.R

import com.google.android.material.card.MaterialCardView

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialCardView>(R.id.cardTempoEmMarte).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_tempoEmMarteFragment)
        }

        view.findViewById<MaterialCardView>(R.id.cardNovasTecnologias).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_tecnologiasUsadasFragment)
        }

        view.findViewById<MaterialCardView>(R.id.cardMarsRover).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_marsRoverFragment)
        }


        view.findViewById<MaterialCardView>(R.id.cardImagensEVideos).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_pesquisaimgvidactivity)
        }


        view.findViewById<MaterialCardView>(R.id.cardEventosNaturais).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_eventosNaturaisActivity)
        }

        view.findViewById<MaterialCardView>(R.id.cardPerfil).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_perfilFragment)
        }


        view.findViewById<MaterialCardView>(R.id.cardImagemDoDia).setOnClickListener {
            val imagem = view.findViewById<ImageView>(R.id.imgImagemDoDia)
            val bundle = bundleOf("Tela" to getString(R.string.menu_comparacao), "Imagem" to R.drawable.apod_2)
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_imagemFragment,bundle)
        }


        view.findViewById<MaterialCardView>(R.id.cardObjetosEmColisao).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_asteroidesFragment)
        }
    }
}