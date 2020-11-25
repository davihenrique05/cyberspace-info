package com.example.cyberspace_info.imagelandscape.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import com.example.cyberspace_info.R


class ImagemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_imagem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("DEPRECATION")
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val tela = arguments?.getString("Tela")
        val imagemId = arguments?.getInt("Imagem")
        var favorito = false
        val imagem = view.findViewById<ImageView>(R.id.imageViewShow)
        val icone = view.findViewById<ImageView>(R.id.imageIconFavorite)
        var visivel = false

        if(imagemId != null){
            imagem.setImageResource(imagemId)
        }

        imagem.setOnClickListener {
            if(!visivel){
                val toolbar = view.findViewById<ConstraintLayout>(R.id.toolbarImagemFrag)
                toolbar.visibility = View.VISIBLE
                visivel = true
            }else{
                val toolbar = view.findViewById<ConstraintLayout>(R.id.toolbarImagemFrag)
                toolbar.visibility = View.GONE
                visivel = false
            }

        }

        icone?.setOnClickListener {
            favorito = if(!favorito){

                icone.setImageResource(R.drawable.ic_unfav)
                true
            }else{
                icone.setImageResource(R.drawable.ic_fav)
                false
            }
        }

        fecharTela(tela!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }

    private fun fecharTela(tela:String){
        when (tela) {
            getString(R.string.perfil_comparacao) -> {
                view?.findViewById<ImageView>(R.id.imageIconClose)?.setOnClickListener {
                    val navController = Navigation.findNavController(requireView())
                    navController.navigate(R.id.action_imagemFragment_to_menuFragment)
                    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    @Suppress("DEPRECATION")
                    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }
            getString(R.string.menu_comparacao) -> {
                view?.findViewById<ImageView>(R.id.imageIconClose)?.setOnClickListener {
                    val navController = Navigation.findNavController(requireView())
                    navController.navigate(R.id.action_imagemFragment_to_menuFragment)
                    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    @Suppress("DEPRECATION")
                    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }
            getString(R.string.galeria_comparacao) -> {
                view?.findViewById<ImageView>(R.id.imageIconClose)?.setOnClickListener {
                    val navController = Navigation.findNavController(requireView())
                    navController.navigate(R.id.action_imagemFragment_to_galeriaFragment)
                    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    @Suppress("DEPRECATION")
                    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }
        }
    }

}