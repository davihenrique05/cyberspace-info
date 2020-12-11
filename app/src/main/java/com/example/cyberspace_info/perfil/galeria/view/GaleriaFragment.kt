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
        val manager = GridLayoutManager(view.context, 3)
        val back = view.findViewById<ImageView>(R.id.imageIconReturnGaleria)
        val tela = arguments?.getString("Origem")

        lateinit var listaDeImagens: MutableList<String>
        lateinit var recylerAdapter: ImagensAdapter

        if (tela == "Perfil") {
            //Chamar a função que popula através do BD
            listaDeImagens = popularLista()

            //Esse Adapter eu fiz com imagens que estavam no Drawable, portanto a Imagem é INT, mas
            //você pode usar a mesma estrutura em baixo e trocar para String que é uma URL que será carregada
            // Pelo Picasso, não se esqueça de passar esse Parametro Tela para que a view de exibir imagem volte
            //para o devido lugar.
            //E a origem para que a tela de imagem possa mandar para a tela anterior também como se organizar com API ou BD
            recylerAdapter = ImagensAdapter(listaDeImagens, true) {
                val navController = Navigation.findNavController(view)
                val bundle = bundleOf(
                    "Tela" to getString(R.string.galeria_comparacao),
                    "Imagem" to it.toInt(),
                    "Origem" to getString(R.string.perfil_comparacao)
                )
                navController.navigate(R.id.action_galeriaFragment_to_imagemFragment, bundle)
            }
        } else {
            if (tela == "MarsRover") {
                listaDeImagens = arguments?.get("imagens") as MutableList<String>
                recylerAdapter = ImagensAdapter(listaDeImagens, false) {
                    val navController = Navigation.findNavController(view)
                    val bundle = bundleOf(
                        "Tela" to getString(R.string.galeria_comparacao),
                        "Imagem" to it,
                        "Origem" to getString(R.string.marsrover_comparacao)
                    )
                    navController.navigate(R.id.action_galeriaFragment_to_imagemFragment, bundle)
                }
            } else {
                //Chamar a função que popula através da API
                val listaDeImagens = popularLista()
            }
        }

        recyler.apply {
            setHasFixedSize(true)
            adapter = recylerAdapter
            layoutManager = manager
        }

        //O botão de retorno nesse caso volta para o perfil
        back.setOnClickListener {
            val navegar = Navigation.findNavController(view)
            if (tela == "Perfil") {

                navegar.navigate(R.id.action_galeriaFragment_to_perfilFragment)
            }else {
                navegar.navigate(R.id.action_galeriaFragment_to_marsRoverFragment)
            }
        }
    }

    private fun popularLista(): MutableList<String> {
        var lista = mutableListOf<String>()

        for (i in 0..20) {
            lista.add(R.drawable.apod_1.toString())
            lista.add(R.drawable.apod_2.toString())
            lista.add(R.drawable.apod_3.toString())
        }

        return lista
    }
}