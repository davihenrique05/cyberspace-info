package com.example.cyberspace_info.pesquisarimgvid

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R

class ResultadosPesquisaFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val minhaView = inflater.inflate(R.layout.fragment_resultados_pesquisa, container, false)
        return minhaView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val viewManager = GridLayoutManager(view.context, 3)
        val recyclerView = view.findViewById<RecyclerView>(R.id.listaFotosVideos)

        var meusPlanetas = listOf(
            FotoVideo(R.drawable.mercurio),
            FotoVideo(R.drawable.venus),
            FotoVideo(R.drawable.terra),
            FotoVideo(R.drawable.marte),
            FotoVideo(R.drawable.jupiter),
            FotoVideo(R.drawable.saturno),
            FotoVideo(R.drawable.urano),
            FotoVideo(R.drawable.netuno)
        )

        val viewAdapter = ResultadosPesquisaFragmentAdpater(meusPlanetas)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        }
    }
}