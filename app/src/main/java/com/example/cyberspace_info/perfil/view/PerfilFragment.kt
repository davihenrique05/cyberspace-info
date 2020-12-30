package com.example.cyberspace_info.perfil.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.example.cyberspace_info.R
import com.example.cyberspace_info.autenticacao.view.AutenticacaoActivity

class PerfilFragment : Fragment() {

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
            activity?.overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
            startActivity(intent)
            activity?.finish()
        }

        view.findViewById<ImageView>(R.id.imageIconReturnPerfil).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_perfilFragment_to_menuFragment)
        }

        view.findViewById<ImageView>(R.id.imageIconGaleria).setOnClickListener {
            val bundle = bundleOf("Origem" to getString(R.string.perfil_comparacao))
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_perfilFragment_to_galeriaFragment,bundle)
        }

        val imagem = view.findViewById<ImageView>(R.id.imageGalery1)
        imagem.setOnClickListener {
            val bundle = bundleOf("Tela" to getString(R.string.perfil_comparacao), "Imagem" to R.drawable.apod_2)
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_perfilFragment_to_imagemFragment, bundle)
        }

        val imagem2 = view.findViewById<ImageView>(R.id.imageGalery2)
        imagem2.setOnClickListener {
            val bundle = bundleOf("Tela" to getString(R.string.perfil_comparacao), "Imagem" to R.drawable.apod_1)
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_perfilFragment_to_imagemFragment, bundle)
        }

        val imagem3 = view.findViewById<ImageView>(R.id.imageGalery3)
        imagem3.setOnClickListener {
            val bundle = bundleOf("Tela" to getString(R.string.perfil_comparacao), "Imagem" to R.drawable.apod_3)
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_perfilFragment_to_imagemFragment, bundle)
        }
    }
}