package com.example.cyberspace_info.listatecnologiasusadas.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listaeventosnaturais.view.adapter.EventoAtualAdapter
import com.example.cyberspace_info.listatecnologiasusadas.model.ProjectDataModel
import com.example.cyberspace_info.listatecnologiasusadas.model.ProjectIdModel
import com.example.cyberspace_info.listatecnologiasusadas.repository.ProjectIdRepository
import com.example.cyberspace_info.listatecnologiasusadas.view.adapter.TecnologiasUsadasAdapter
import com.example.cyberspace_info.listatecnologiasusadas.viewmodel.ProjectIdViewModel

class TecnologiasUsadasFragment : Fragment() {

    lateinit var _viewModelProject : ProjectIdViewModel
    lateinit var _listaProjetos : MutableList<ProjectDataModel>
    lateinit var _adaptador : TecnologiasUsadasAdapter
    lateinit var _listaIdProjeto : MutableList<ProjectIdModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _listaProjetos = mutableListOf()
        _listaIdProjeto = mutableListOf()
        _adaptador = TecnologiasUsadasAdapter(_listaProjetos){

            val bottomSheetFragment =  BottomSheetFragment();

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

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTecnologiasUsadas)
        var linearManager = LinearLayoutManager(view.context)

        recyclerView.apply{

            setHasFixedSize(true)
            layoutManager = linearManager
            adapter = _adaptador

        }
        
        _viewModelProject.getAllIdsProjects().observe(viewLifecycleOwner,{

              _listaIdProjeto.addAll(it)

               _viewModelProject.getUniqueObjectProject(_listaIdProjeto).observe(viewLifecycleOwner,{
                   listarResultados(it)
               })

        })

    }

    fun listarResultados(lista:List<ProjectDataModel>){
        _listaProjetos.addAll(lista)
        _adaptador.notifyDataSetChanged()
    }
}

