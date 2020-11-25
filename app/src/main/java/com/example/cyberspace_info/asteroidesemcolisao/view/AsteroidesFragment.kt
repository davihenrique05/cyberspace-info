package com.example.cyberspace_info.asteroidesemcolisao.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.asteroidesemcolisao.model.Asteroide
import com.example.cyberspace_info.asteroidesemcolisao.model.Velocidade
import com.example.cyberspace_info.asteroidesemcolisao.view.adapter.AsteroidesAdapter
import com.example.cyberspace_info.listatecnologiasusadas.view.BottomSheetFragment
import com.example.cyberspace_info.perfil.galeria.view.adapter.ImagensAdapter

class AsteroidesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asteroides, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyler = view.findViewById<RecyclerView>(R.id.recyclerViewAsteroides)
        val manager = LinearLayoutManager(view.context)
        val lista = popularLista()
        val bottomSheetFragment =  BottomSheetAsteroideFragment()

        val recylerAdapter = AsteroidesAdapter(lista){
            bottomSheetFragment.show((activity as AppCompatActivity).supportFragmentManager,"BottomSheetDialog")
        }

        val back = view.findViewById<ImageView>(R.id.imageIconReturnAsteroides)

        recyler.apply {
            setHasFixedSize(true)
            adapter = recylerAdapter
            layoutManager = manager
        }

        back.setOnClickListener {
            val navegar = Navigation.findNavController(view)
            navegar.navigate(R.id.action_asteroidesFragment_to_menuFragment)
        }

    }

    private fun popularLista(): MutableList<Asteroide> {
        val asteroide = Asteroide("465633 (2009 JR5)",
            "http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=2465633",
            231.5021,517.6544,
            "2015-09-08",
            Velocidade(65260.6370))

        val lista = mutableListOf<Asteroide>()

        for(i in 0..15){
            lista.add(asteroide)
        }

        return lista
    }

}