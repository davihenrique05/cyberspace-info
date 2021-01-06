package com.example.cyberspace_info.listatecnologiasusadas.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listatecnologiasusadas.model.ProjectDataModel
import com.example.cyberspace_info.listatecnologiasusadas.model.ProjectFilterModel
import com.example.cyberspace_info.listatecnologiasusadas.repository.ProjectIdRepository
import com.example.cyberspace_info.listatecnologiasusadas.view.adapter.TecnologiasUsadasAdapter
import com.example.cyberspace_info.listatecnologiasusadas.viewmodel.ProjectIdViewModel
import com.google.android.material.card.MaterialCardView


class ResultsSearchTecnologiasUsadasFragment : Fragment() {

    private lateinit var _viewModelProject : ProjectIdViewModel
    private lateinit var _listaProjetos : MutableList<ProjectDataModel>
    private lateinit var _adaptador : TecnologiasUsadasAdapter
    private lateinit var _listaIdProjeto : MutableList<ProjectFilterModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _listaProjetos = mutableListOf()
        _listaIdProjeto = mutableListOf()
        _adaptador = TecnologiasUsadasAdapter(_listaProjetos){

            val bottomSheetFragment =  BottomSheetFragment()

            val bundle = bundleOf("title" to it.title,
                "description" to it.description
            )

            bottomSheetFragment.arguments = bundle
            bottomSheetFragment.show((activity as AppCompatActivity).supportFragmentManager,"BottomSheetDialog")

        }

        _viewModelProject = ViewModelProvider(this,ProjectIdViewModel.ProjectIdViewModelFactory(
            ProjectIdRepository()
        )).get(ProjectIdViewModel::class.java)

        return inflater.inflate(
            R.layout.fragment_results_search_tecnologias_usadas,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)
        view.findViewById<ImageView>(R.id.imgReturn).setOnClickListener {
            navController.navigate(R.id.action_tecnologiasUsadasFragment_to_menuFragment)
        }

        val progresBar = view.findViewById<ProgressBar>(R.id.progessBar)

        showLoading(true)
        val color = ContextCompat.getColor(view.context,R.color.colorPrimaryDarkest)
        @Suppress("DEPRECATION")
        progresBar.indeterminateDrawable.setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY)

        setRecyclerViewWithCoroutine(view)
    }

    private fun setRecyclerViewWithCoroutine(view: View){

        val objectId = arguments?.getString("objectId")
        val search = arguments?.getString("searchQuery").toString()
        val mission = arguments?.getString("missionDirectory").toString()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTecnologiasUsadasFilter)
        val linearManager = LinearLayoutManager(view.context)

        recyclerView.apply{
            setHasFixedSize(true)
            layoutManager = linearManager
            adapter = _adaptador
        }

        _viewModelProject.getAllIdsFilteredProject(objectId,search,mission).observe(viewLifecycleOwner) { it ->

            if(it[0].projects.isNullOrEmpty()){
                showLoading(false)
                view.findViewById<MaterialCardView>(R.id.cardNotFound).visibility = View.VISIBLE
            }else{

                _listaIdProjeto.addAll(it)

                for(i in 0..40) {
                    _viewModelProject.getUniqueObjectProject(_listaIdProjeto[0].projects[i]).observe(viewLifecycleOwner) {
                        if (!it.isNullOrEmpty()) {
                            listarResultados(it)
                        }
                    }
                }

            }


        }

    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = view?.findViewById<View>(R.id.loading)

        if (isLoading) {
            viewLoading?.visibility = View.VISIBLE
        } else {
            viewLoading?.visibility = View.GONE
        }
    }

    private fun listarResultados(lista:List<ProjectDataModel>){
        _listaProjetos.clear()
        _listaProjetos.addAll(lista)
        showLoading(false)
        _adaptador.notifyDataSetChanged()
    }

}