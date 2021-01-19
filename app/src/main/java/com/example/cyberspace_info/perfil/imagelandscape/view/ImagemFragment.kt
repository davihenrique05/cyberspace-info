package com.example.cyberspace_info.perfil.imagelandscape.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.example.cyberspace_info.R
import com.example.cyberspace_info.db.ImagemDatabase
import com.example.cyberspace_info.perfil.entity.ImagemEntity
import com.example.cyberspace_info.perfil.repository.ImagemRepository
import com.example.cyberspace_info.perfil.viewmodel.ImagemViewModel
import com.squareup.picasso.Picasso


class ImagemFragment : Fragment() {

    lateinit var _viewModel: ImagemViewModel
    private var _listaDeImagens = mutableListOf<ImagemEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_imagem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("DEPRECATION")
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val imagemUrl = arguments?.getString("Imagem")


        val imagem = view.findViewById<ImageView>(R.id.imageViewShow)
        val icone = view.findViewById<ImageView>(R.id.imageIconFavorite)

        if (imagemUrl != null) {
            Picasso.get()
                .load(imagemUrl)
                .into(imagem)
        }

        _viewModel = ViewModelProvider(
            this,
            ImagemViewModel.ImagemViewModelFacytory(
                ImagemRepository(
                    ImagemDatabase.getDataBase(view.context).imagemDao()
                )
            )
        ).get(ImagemViewModel::class.java)


        _viewModel.obterImagems().observe(viewLifecycleOwner) {
            _listaDeImagens.clear()
            _listaDeImagens.addAll(it)

            var verificar = verificarImagem(imagemUrl)
            definirIcone(icone, verificar)

            icone?.setOnClickListener {

                if (verificar) {
                    removerImagem(imagemUrl)
                    verificar = verificarImagem(imagemUrl)
                    definirIcone(icone, false)
                } else {
                    favoritarImagem(imagemUrl)
                    verificar = verificarImagem(imagemUrl)
                    definirIcone(icone, true)
                }
            }
        }

        mostrarToolbar(imagem)
        fecharTela()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }

    private fun favoritarImagem(url: String?) {
        if (url != null) {
            _viewModel.salvarImagem(url).observe(viewLifecycleOwner) {
                val toast = Toast.makeText(
                    requireView().context,
                    getString(R.string.favorito),
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }

    private fun verificarImagem(urlImagem: String?): Boolean {

        _listaDeImagens.forEach {
            if (it.url == urlImagem) {
                return true
            }
        }
        return false
    }

    private fun removerImagem(urlImagem: String?) {

        if (urlImagem != null) {
            _viewModel.deletarImagem(urlImagem).observe(viewLifecycleOwner) {
                val toast = Toast.makeText(
                    requireView().context,
                    getString(R.string.removido),
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }

    private fun mostrarToolbar(imagem: ImageView) {
        var visivel = false
        imagem.setOnClickListener {
            if (!visivel) {
                val toolbar = requireView().findViewById<ConstraintLayout>(R.id.toolbarImagemFrag)
                toolbar.visibility = View.VISIBLE
                visivel = true
            } else {
                val toolbar = requireView().findViewById<ConstraintLayout>(R.id.toolbarImagemFrag)
                toolbar.visibility = View.GONE
                visivel = false
            }

        }
    }

    private fun definirIcone(icone: ImageView, favorito: Boolean) {
        if (favorito) {
            icone.setImageResource(R.drawable.ic_unfav)
        } else {
            icone.setImageResource(R.drawable.ic_fav)
        }
    }

    private fun fecharTela() {

        view?.findViewById<ImageView>(R.id.imageIconClose)?.setOnClickListener {
            val navController = Navigation.findNavController(requireView())
            navController.popBackStack()
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            @Suppress("DEPRECATION")
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

    }

}