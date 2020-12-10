package com.example.cyberspace_info.menu.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.example.cyberspace_info.R
import com.example.cyberspace_info.asteroidesemcolisao.repository.AsteroidesRepository
import com.example.cyberspace_info.asteroidesemcolisao.viewmodel.AsteroidesEmColisaoViewModel
import com.example.cyberspace_info.menu.imagemdodia.repository.ImageModelRepository
import com.example.cyberspace_info.menu.imagemdodia.viewmodel.ImageViewModel

import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

class MenuFragment : Fragment() {

    lateinit var _url: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = view.findViewById<TextView>(R.id.txtImagemTittle)
        val image = view.findViewById<ImageView>(R.id.imgImagemDoDia)
        val viewModel = ViewModelProvider(this, ImageViewModel.ImageViewModelFactory(
            ImageModelRepository()
        )).get(ImageViewModel::class.java)

        viewModel.obterImagemDoDia().observe(viewLifecycleOwner) {
            title.text = it.tittle
            _url = it.url
            Picasso.get()
                .load(it.url)
                .into(image)
        }

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
            navController.navigate(R.id.action_menuFragment_to_eventosNaturaisFragment)
        }

        view.findViewById<MaterialCardView>(R.id.cardPerfil).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_perfilFragment)
        }


        view.findViewById<MaterialCardView>(R.id.cardImagemDoDia).setOnClickListener {
            val bundle = bundleOf("Tela" to getString(R.string.menu_comparacao), "Imagem" to _url)
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_imagemFragment,bundle)
        }


        view.findViewById<MaterialCardView>(R.id.cardObjetosEmColisao).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_asteroidesFragment)
        }

        view.findViewById<MaterialCardView>(R.id.cardPlanetasOrbitando).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_planetasorbitandoestrelasActivity)
        }
    }
}