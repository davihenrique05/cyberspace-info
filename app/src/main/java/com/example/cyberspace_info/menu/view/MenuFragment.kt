package com.example.cyberspace_info.menu.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import com.example.cyberspace_info.R
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso


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

        Picasso.get()
            .load("https://api.nasa.gov/assets/img/general/apod.jpg")
            .into(view.findViewById<ImageView>(R.id.imgImagemDoDia))

        view.findViewById<MaterialCardView>(R.id.cardTempoEmMarte).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_tempoEmMarteFragment)
        }

        view.findViewById<MaterialCardView>(R.id.cardMarsRover).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_marsRoverFragment)
        }

        //Bonner: Tentei por com fragment, não deu certo, vou por activity, mas vou deixar aqui caso
        //seja necessário usar fragment, se não removo
        /*view.findViewById<MaterialCardView>(R.id.cardImagensEVideos).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_pesquisaImgVidFragment)
        }

        view.findViewById<MaterialCardView>(R.id.cardPlanetasOrbitando).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_planetasOrbitandoEstrelas)
        }*/

    }
}