package com.example.cyberspace_info.pesquisarimgvid.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.pesquisarimgvid.view.adapter.ResultadosPesquisaFragmentAdpater
import com.example.cyberspace_info.pesquisarimgvid.model.FotoVideoModel

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
            FotoVideoModel(R.drawable.mercurio),
            FotoVideoModel(R.drawable.venus),
            FotoVideoModel(R.drawable.terra),
            FotoVideoModel(R.drawable.marte),
            FotoVideoModel(R.drawable.jupiter),
            FotoVideoModel(R.drawable.saturno),
            FotoVideoModel(R.drawable.urano),
            FotoVideoModel(R.drawable.netuno),

            FotoVideoModel(R.drawable.mercurio),
            FotoVideoModel(R.drawable.venus),
            FotoVideoModel(R.drawable.terra),
            FotoVideoModel(R.drawable.marte),
            FotoVideoModel(R.drawable.jupiter),
            FotoVideoModel(R.drawable.saturno),
            FotoVideoModel(R.drawable.urano),
            FotoVideoModel(R.drawable.netuno),

            FotoVideoModel(R.drawable.mercurio),
            FotoVideoModel(R.drawable.venus),
            FotoVideoModel(R.drawable.terra),
            FotoVideoModel(R.drawable.marte),
            FotoVideoModel(R.drawable.jupiter),
            FotoVideoModel(R.drawable.saturno),
            FotoVideoModel(R.drawable.urano),
            FotoVideoModel(R.drawable.netuno)
        )

        val viewAdapter =
            ResultadosPesquisaFragmentAdpater(
                meusPlanetas
            )
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        }
    }
}