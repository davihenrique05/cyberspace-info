package com.example.cyberspace_info.listatecnologiasusadas.view

import android.os.Bundle
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
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listatecnologiasusadas.model.ProjectDataModel
import com.example.cyberspace_info.listatecnologiasusadas.model.ProjectIdModel
import com.example.cyberspace_info.listatecnologiasusadas.repository.ProjectIdRepository
import com.example.cyberspace_info.listatecnologiasusadas.view.adapter.TecnologiasUsadasAdapter
import com.example.cyberspace_info.listatecnologiasusadas.viewmodel.ProjectIdViewModel

class TecnologiasUsadasFragment : Fragment() {

    private lateinit var _viewModelProject : ProjectIdViewModel
    private lateinit var _listaProjetos : MutableList<ProjectDataModel>
    private lateinit var _adaptador : TecnologiasUsadasAdapter
    private lateinit var _listaIdProjeto : MutableList<ProjectIdModel>

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

        _viewModelProject = ViewModelProvider(this,ProjectIdViewModel.ProjectIdViewModelFactory(ProjectIdRepository()
        )).get(ProjectIdViewModel::class.java)

        return inflater.inflate(R.layout.fragment_tecnologias_usadas, container, false)

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

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTecnologiasUsadas)
        val linearManager = LinearLayoutManager(view.context)

        recyclerView.apply{

            setHasFixedSize(true)
            layoutManager = linearManager
            adapter = _adaptador

        }
        
        _viewModelProject.getAllIdsProjects().observe(viewLifecycleOwner) { it ->

            _listaIdProjeto.addAll(it)

            _viewModelProject.getUniqueObjectProject(_listaIdProjeto).observe(viewLifecycleOwner) {
                if(!it.isNullOrEmpty()) {
                    listarResultados(it)
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
        _listaProjetos.addAll(lista)
        showLoading(false)
        _adaptador.notifyDataSetChanged()
    }
}

