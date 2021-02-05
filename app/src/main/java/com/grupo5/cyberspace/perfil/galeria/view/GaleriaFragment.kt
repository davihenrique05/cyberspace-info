package com.grupo5.cyberspace.perfil.galeria.view

import android.os.Bundle
import android.service.autofill.ImageTransformation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.db.ImagemDatabase
import com.grupo5.cyberspace.perfil.entity.ImagemEntity
import com.grupo5.cyberspace.perfil.galeria.view.adapter.ImagensAdapter
import com.grupo5.cyberspace.perfil.repository.ImagemRepository
import com.grupo5.cyberspace.perfil.viewmodel.ImagemViewModel
import com.google.firebase.auth.FirebaseAuth


class GaleriaFragment : Fragment() {

    private lateinit var _viewModel: ImagemViewModel
    private lateinit var _recylerAdapter: ImagensAdapter
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
        val progresBar = view.findViewById<ProgressBar>(R.id.progessBarMars)

        val color = ContextCompat.getColor(view.context, R.color.colorWhite)
        @Suppress("DEPRECATION")
        progresBar.indeterminateDrawable.setColorFilter(
            color,
            android.graphics.PorterDuff.Mode.MULTIPLY
        )

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
            _listaDeImagens.clear()
            showLoading(true)
            view.findViewById<TextView>(R.id.txtTituloGal).text = getString(R.string.mars_rover)
            val lista = arguments?.get("imagens") as MutableList<String>
            if(lista.isNotEmpty()){
                atribuirImagensRover(lista)
            }else{
                showLoading(false)
                view.findViewById<ImageView>(R.id.imgVazioGaleria).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.txtEmptyRover).visibility= View.VISIBLE
            }
        }

        if(_listaDeImagens.isNotEmpty()){
            recyler.apply {
                setHasFixedSize(true)
                adapter = _recylerAdapter
                layoutManager = manager
            }
        }

        navegacaoEntreTelas(tela)
    }


    private fun carregarImagensFavoritas() {

        val user = FirebaseAuth.getInstance().currentUser
        _recylerAdapter = ImagensAdapter(_listaDeImagens) {
            val navController = Navigation.findNavController(requireView())
            val bundle = bundleOf("Imagem" to it)
            navController.navigate(R.id.action_galeriaFragment_to_imagemFragment, bundle)
        }

        _viewModel.obterImagems(user!!.uid).observe(viewLifecycleOwner) {
            val listaUrl = extrairUrl(it)
            _listaDeImagens.clear()
            _listaDeImagens.addAll(listaUrl)
            _recylerAdapter.notifyDataSetChanged()

            if(_listaDeImagens.isEmpty()){
                val imagemVazio = requireView().findViewById<ImageView>(R.id.imgVazioGaleria)

                imagemVazio.visibility = View.VISIBLE
            }
        }
    }

    private fun navegacaoEntreTelas(tela: String?) {

        val back = requireView().findViewById<ImageView>(R.id.imageIconReturnGaleria)
        back.setOnClickListener {
            val navegar = Navigation.findNavController(requireView())
            navegar.popBackStack()
        }
    }

    private fun extrairUrl(lista: MutableList<ImagemEntity>): MutableList<String> {
        val listaDeUrl = mutableListOf<String>()
        val ultimo = lista.size - 1

        for (i in ultimo downTo 0) {
            val imagem = lista[i]
            listaDeUrl.add(imagem.url)
        }

        return listaDeUrl
    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = view?.findViewById<View>(R.id.loadingMars)

        if (isLoading) {
            viewLoading?.visibility = View.VISIBLE
        } else {
            viewLoading?.visibility = View.GONE
        }
    }

    private fun atribuirImagensRover(lista: MutableList<String>) {

        showLoading(false)
        _listaDeImagens.addAll(lista)
        _recylerAdapter = ImagensAdapter(_listaDeImagens) {
            val navController = Navigation.findNavController(requireView())
            val bundle = bundleOf("Imagem" to it)
            navController.navigate(R.id.action_galeriaFragment_to_imagemFragment, bundle)
        }
        _recylerAdapter.notifyDataSetChanged()
    }

}