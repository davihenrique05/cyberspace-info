package com.example.cyberspace_info.perfil.galeria.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.db.ImagemDatabase
import com.example.cyberspace_info.perfil.entity.ImagemEntity
import com.example.cyberspace_info.perfil.galeria.view.adapter.ImagensAdapter
import com.example.cyberspace_info.perfil.repository.ImagemRepository
import com.example.cyberspace_info.perfil.viewmodel.ImagemViewModel


class GaleriaFragment : Fragment() {

    lateinit var _viewModel: ImagemViewModel
    lateinit var _recylerAdapter: ImagensAdapter
    private var _listaDeImagens = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_galeria, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyler = view.findViewById<RecyclerView>(R.id.recyclerViewGaleria)
        val manager = GridLayoutManager(view.context, 3)
        val tela = arguments?.getString("Origem")


        _viewModel = ViewModelProvider(
            this,
            ImagemViewModel.ImagemViewModelFacytory(
                ImagemRepository(
                    ImagemDatabase.getDataBase(view.context).imagemDao()
                )
            )
        ).get(ImagemViewModel::class.java)

        if (tela == "Perfil") {

            carregarImagensFavoritas()

        } else if (tela == "MarsRover") {
            _listaDeImagens = arguments?.get("imagens") as MutableList<String>
            _recylerAdapter = ImagensAdapter(_listaDeImagens) {
                val navController = Navigation.findNavController(requireView())
                val bundle = bundleOf(
                    "Tela" to getString(R.string.galeria_comparacao),
                    "Imagem" to it,
                    "Origem" to getString(R.string.marsrover_comparacao)
                )
                navController.navigate(R.id.action_galeriaFragment_to_imagemFragment, bundle)
            }
        }

        recyler.apply {
            setHasFixedSize(true)
            adapter = _recylerAdapter
            layoutManager = manager
        }

        navegacaoEntreTelas(tela)
    }


    fun carregarImagensFavoritas() {

        _recylerAdapter = ImagensAdapter(_listaDeImagens) {
            val navController = Navigation.findNavController(requireView())
            val bundle = bundleOf(
                "Tela" to getString(R.string.galeria_comparacao),
                "Imagem" to it,
                "Origem" to getString(R.string.perfil_comparacao)
            )
            navController.navigate(R.id.action_galeriaFragment_to_imagemFragment, bundle)
        }

        _viewModel.obterImagems().observe(viewLifecycleOwner) {
            val listaUrl = extrairUrl(it)
            _listaDeImagens.clear()
            _listaDeImagens.addAll(listaUrl)
            _recylerAdapter.notifyDataSetChanged()
        }
    }

    fun navegacaoEntreTelas(tela: String?) {

        val back = requireView().findViewById<ImageView>(R.id.imageIconReturnGaleria)
        back.setOnClickListener {
            val navegar = Navigation.findNavController(requireView())
            if (tela == "Perfil") {
                navegar.navigate(R.id.action_galeriaFragment_to_perfilFragment)
            } else {
                navegar.navigate(R.id.action_galeriaFragment_to_marsRoverFragment)
            }
        }
    }

    fun extrairUrl(lista: MutableList<ImagemEntity>): MutableList<String> {
        val listaDeUrl = mutableListOf<String>()
        val ultimo = lista.size - 1

        for (i in ultimo downTo 0) {
            val imagem = lista[i]
            listaDeUrl.add(imagem.url)
        }

        return listaDeUrl
    }
}