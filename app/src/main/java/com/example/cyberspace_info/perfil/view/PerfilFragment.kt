package com.example.cyberspace_info.perfil.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import com.example.cyberspace_info.R
import com.example.cyberspace_info.autenticacao.view.AutenticacaoActivity

class PerfilFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.imageIconLogOut).setOnClickListener {
            val intent = Intent(view.context, AutenticacaoActivity::class.java)
            startActivity(intent)
        }

        view.findViewById<ImageView>(R.id.imageIconReturnPerfil).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_perfilFragment_to_menuFragment)
        }

        view.findViewById<ImageView>(R.id.imageIconGaleria).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_perfilFragment_to_galeriaFragment)
        }

        view.findViewById<ImageView>(R.id.imageGalery1).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_perfilFragment_to_galeriaFragment)
        }

        view.findViewById<ImageView>(R.id.imageGalery1).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_perfilFragment_to_galeriaFragment)
        }

        view.findViewById<ImageView>(R.id.imageGalery1).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_perfilFragment_to_galeriaFragment)
        }
    }
}