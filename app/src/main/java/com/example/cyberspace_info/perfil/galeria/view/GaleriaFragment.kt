package com.example.cyberspace_info.perfil.galeria.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.perfil.galeria.view.adapter.ImagensAdapter


class GaleriaFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        val listaDeImagens = popularLista()
        val manager = GridLayoutManager(view.context,3)
        val back = view.findViewById<ImageView>(R.id.imageIconReturnGaleria)
        val recylerAdapter = ImagensAdapter(listaDeImagens) {
            val navController = Navigation.findNavController(view)
            val bundle = bundleOf("Tela" to getString(R.string.galeria_comparacao), "Imagem" to it.toInt())
            navController.navigate(R.id.action_galeriaFragment_to_imagemFragment, bundle)
        }

        recyler.apply {
            setHasFixedSize(true)
            adapter = recylerAdapter
            layoutManager = manager
        }

        back.setOnClickListener {
            val navegar = Navigation.findNavController(view)
            navegar.navigate(R.id.action_galeriaFragment_to_perfilFragment)
        }
    }

    private fun popularLista(): MutableList<String> {
        var lista = mutableListOf<String>()

        for(i in 0..20){
            lista.add(R.drawable.apod_1.toString())
            lista.add(R.drawable.apod_2.toString())
            lista.add(R.drawable.apod_3.toString())
        }

        return lista
    }
}