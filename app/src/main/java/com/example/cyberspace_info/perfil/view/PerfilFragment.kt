package com.example.cyberspace_info.perfil.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.example.cyberspace_info.R
import com.example.cyberspace_info.autenticacao.view.AutenticacaoActivity
import com.example.cyberspace_info.db.ImagemDatabase
import com.example.cyberspace_info.perfil.entity.ImagemEntity
import com.example.cyberspace_info.perfil.repository.ImagemRepository
import com.example.cyberspace_info.perfil.viewmodel.ImagemViewModel
import com.squareup.picasso.Picasso

class PerfilFragment : Fragment() {

    lateinit var _viewModel: ImagemViewModel
    private var _listaDeImagens = mutableListOf<ImagemEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagem = view.findViewById<ImageView>(R.id.imageGalery1)
        val imagem2 = view.findViewById<ImageView>(R.id.imageGalery2)
        val imagem3 = view.findViewById<ImageView>(R.id.imageGalery3)
        val backView = view.findViewById<View>(R.id.viewListaVazia)
        val mensagem = view.findViewById<TextView>(R.id.txtListaVazia)

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

            if (_listaDeImagens.isEmpty()) {
                backView.visibility = View.VISIBLE
                mensagem.visibility = View.VISIBLE
            } else {
                backView.visibility = View.GONE
                mensagem.visibility = View.GONE
                var urls = mutableListOf<String>()
                val ultimaPosicao = _listaDeImagens.size - 1

                for (i in ultimaPosicao downTo 0) {
                    urls.add(_listaDeImagens[i].url)
                }

                for (i in urls) {
                    var posicao = urls.indexOf(i)

                    if (posicao == 0) {
                        atribuirImagem(imagem, i)
                        abrirImagem(imagem, i)

                    } else if (posicao == 1) {
                        atribuirImagem(imagem2, i)
                        abrirImagem(imagem2, i)

                    } else if (posicao == 2) {
                        atribuirImagem(imagem3, i)
                        abrirImagem(imagem3, i)

                    } else {
                        break
                    }
                }
            }
        }

        view.findViewById<ImageView>(R.id.imageIconReturnPerfil).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_perfilFragment_to_menuFragment)
        }

        view.findViewById<ImageView>(R.id.imageIconLogOut).setOnClickListener {
            val intent = Intent(view.context, AutenticacaoActivity::class.java)
            activity?.overridePendingTransition(
                R.anim.fragment_fade_enter,
                R.anim.fragment_fade_exit
            )
            startActivity(intent)
            activity?.finish()
        }

        view.findViewById<ImageView>(R.id.imageIconGaleria).setOnClickListener {
            val bundle = bundleOf("Origem" to getString(R.string.perfil_comparacao))
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_perfilFragment_to_galeriaFragment, bundle)
        }

    }

    fun atribuirImagem(imagem: ImageView, url: String) {
        Picasso.get()
            .load(url)
            .into(imagem)
    }

    fun abrirImagem(imagem: ImageView, url: String) {
        imagem.setOnClickListener {
            val bundle = bundleOf("Tela" to getString(R.string.perfil_comparacao), "Imagem" to url)
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_perfilFragment_to_imagemFragment, bundle)
        }
    }
}