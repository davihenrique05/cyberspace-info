package com.grupo5.cyberspace.listaeventosnaturais.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import com.grupo5.cyberspace.R
import com.squareup.picasso.Picasso


class ImagemEventosNaturaisFragment : Fragment() {

    private lateinit var _view : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_imagem_eventos_naturais, container, false)
        _view = view
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val latitude = arguments?.getString("latitude")
        val longitude = arguments?.getString("longitude")
        val imageEvent = view.findViewById<ImageView>(R.id.imgNaturalEvent)

        view.findViewById<ImageView>(R.id.imgCloseEventoNatural).setOnClickListener {
            val navController = Navigation.findNavController(_view)
            navController.navigate(R.id.action_imagemEventosNaturaisFragment_to_eventosNaturaisFragment)
        }

        val coordenadasUrl = "https://maps.googleapis.com/maps/api/staticmap?" + "center=${longitude},${latitude}&" + "zoom=11&size=250x250&key=AIzaSyDFf6J-wAKE1OS-K7EYkn_pbznUhy55J2w"

        Log.i("IMAGEM : ", coordenadasUrl)
        Picasso.get().load(coordenadasUrl).into(imageEvent)

    }

}