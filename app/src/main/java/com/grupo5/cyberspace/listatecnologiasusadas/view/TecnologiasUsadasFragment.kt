package com.grupo5.cyberspace.listatecnologiasusadas.view

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.listatecnologiasusadas.model.ProjectDataModel
import com.grupo5.cyberspace.listatecnologiasusadas.model.ProjectIdModel
import com.grupo5.cyberspace.listatecnologiasusadas.repository.ProjectIdRepository
import com.grupo5.cyberspace.listatecnologiasusadas.view.adapter.TecnologiasUsadasAdapter
import com.grupo5.cyberspace.listatecnologiasusadas.viewmodel.ProjectIdViewModel

class TecnologiasUsadasFragment : Fragment() {

    private lateinit var _viewModelProject: ProjectIdViewModel
    private lateinit var _listaProjetos: MutableList<ProjectDataModel>
    private lateinit var _adaptador: TecnologiasUsadasAdapter
    private lateinit var _listaIdProjeto: MutableList<ProjectIdModel>
    private lateinit var linearManager : LinearLayoutManager
    private var currentItens = 0
    private var totalItens = 0
    private var scrollOutItens = 0
    var lastItemCompare = 0
    private lateinit var _view : View

    //layoutManager.getChildCount() -> Total itens na tela
    //layoutManager.getItemCount() -> Total de itens no adapter
    //layoutManager.findFirstVisibleItemPosition() -> Itens scrollados

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _listaProjetos = mutableListOf()
        _listaIdProjeto = mutableListOf()
        _adaptador = TecnologiasUsadasAdapter(_listaProjetos) {

            val bottomSheetFragment = BottomSheetFragment()

            val bundle = bundleOf(
                "title" to it.title,
                "description" to it.description
            )

            bottomSheetFragment.arguments = bundle
            bottomSheetFragment.show(
                (activity as AppCompatActivity).supportFragmentManager,
                "BottomSheetDialog"
            )

        }

        _viewModelProject = ViewModelProvider(
            this, ProjectIdViewModel.ProjectIdViewModelFactory(
                ProjectIdRepository()
            )
        ).get(ProjectIdViewModel::class.java)

        val view =  inflater.inflate(R.layout.fragment_tecnologias_usadas, container, false)
        _view = view
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)
        view.findViewById<ImageView>(R.id.imgReturn).setOnClickListener {
            START_VALUE_LIST = 0
            navController.navigate(R.id.action_tecnologiasUsadasFragment_to_menuFragment)
        }

        createProgressBar(view)
        showLoading(true)
        recyclerviewItens(view)

    }

    private fun recyclerviewItens(view: View) {

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTecnologiasUsadas)
        linearManager = LinearLayoutManager(view.context)

        recyclerView.apply {

            setHasFixedSize(true)
            layoutManager = linearManager
            adapter = _adaptador

        }

        var firstVisible = linearManager.findFirstVisibleItemPosition()

        pushInListFirstItens()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val currentFirstVisible = linearManager.findFirstVisibleItemPosition()

                firstVisible = currentFirstVisible

                val beforeScrollTotal = START_VALUE_LIST

                currentItens = linearManager.childCount
                totalItens = linearManager.itemCount
                scrollOutItens = linearManager.findFirstVisibleItemPosition()

                pushDinamicItensWithSleep(dy,beforeScrollTotal)

            }

        })
    }

    private fun pushDinamicItensWithSleep(dy:Int,beforeScrollTotal:Int){

        val lastItemCurrent = linearManager.findLastVisibleItemPosition()

        if(!(dy < START_VALUE_LIST && scrollOutItens >= START_VALUE_LIST && beforeScrollTotal == START_VALUE_LIST)){

            if(lastItemCurrent >= lastItemCompare){

                lastItemCompare = lastItemCurrent

                if((lastItemCompare <=  _listaIdProjeto.size) && lastItemCompare == (TOTAL_INITAL_ITENS-1)){

                    SystemClock.sleep(2000)

                    for (i in (lastItemCompare)..(lastItemCompare+ITENS_AFTER_UPDATE)) {

                        _viewModelProject.getUniqueObjectProject(_listaIdProjeto[i])
                            .observe(viewLifecycleOwner) {
                                if (!it.isNullOrEmpty()) {
                                    listarResultados(it)
                                }
                            }
                    }

                    TOTAL_INITAL_ITENS = TOTAL_INITAL_ITENS + ITENS_AFTER_UPDATE

                }
            }

        }

    }

    private fun pushInListFirstItens(){

        _viewModelProject.getAllIdsProjects().observe(viewLifecycleOwner) { it ->

            _listaIdProjeto.addAll(it)

            for (i in START_VALUE_LIST..INDEX_START_VALUE) {

                _viewModelProject.getUniqueObjectProject(_listaIdProjeto[i])
                    .observe(viewLifecycleOwner) {
                        if (!it.isNullOrEmpty()) {
                            listarResultados(it)
                        }
                    }
            }
        }

    }

    private fun createProgressBar(view: View) {

        val progresBar = view.findViewById<ProgressBar>(R.id.progessBar)
        val color = ContextCompat.getColor(view.context, R.color.colorWhite)
        @Suppress("DEPRECATION")
        progresBar.indeterminateDrawable.setColorFilter(
            color,
            android.graphics.PorterDuff.Mode.MULTIPLY
        )

    }


    private fun showLoading(isLoading: Boolean) {
        val viewLoading = view?.findViewById<View>(R.id.loading)

        if (isLoading) {
            viewLoading?.visibility = View.VISIBLE
        } else {
            viewLoading?.visibility = View.GONE
        }
    }

    private fun listarResultados(lista: List<ProjectDataModel>) {
        _listaProjetos.clear()
        _listaProjetos.addAll(lista)
        showLoading(false)
        _adaptador.notifyDataSetChanged()
    }

    companion object{
        var TOTAL_INITAL_ITENS = 12
        var ITENS_AFTER_UPDATE = 12
        var START_VALUE_LIST = 0
        var INDEX_START_VALUE = 11
    }

}

