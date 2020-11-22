package com.example.cyberspace_info.menu.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listaeventosnaturais.view.EventosNaturaisActivity
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

        view.findViewById<MaterialCardView>(R.id.cardEventosNaturais).setOnClickListener {

            //val navController = Navigation.findNavController(view)
            //navController.navigate(R.id.action_menuFragment_to_eventosNaturaisFragment)

            val intent = Intent (getActivity(), EventosNaturaisActivity::class.java)
            getActivity()?.startActivity(intent)


        }
    }
}