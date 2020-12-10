package com.example.cyberspace_info.asteroidesemcolisao.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.asteroidesemcolisao.model.AsteroideModel
import com.example.cyberspace_info.asteroidesemcolisao.repository.AsteroidesRepository
import com.example.cyberspace_info.asteroidesemcolisao.view.adapter.AsteroidesAdapter
import com.example.cyberspace_info.asteroidesemcolisao.viewmodel.AsteroidesEmColisaoViewModel

class AsteroidesFragment : Fragment() {

    private var _lista = mutableListOf<AsteroideModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asteroides, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this,AsteroidesEmColisaoViewModel.AsteroidesEmColisaoViewModelFactory(
            AsteroidesRepository()
        )).get(AsteroidesEmColisaoViewModel::class.java)

        val recyler = view.findViewById<RecyclerView>(R.id.recyclerViewAsteroides)
        val manager = LinearLayoutManager(view.context)

        val recylerAdapter = AsteroidesAdapter(_lista){
            viewModel.showBottomSheet(view.context, it)
        }

        recyler.apply {
            setHasFixedSize(true)
            adapter = recylerAdapter
            layoutManager = manager
        }

        view.findViewById<ImageView>(R.id.imageIconReturnAsteroides).setOnClickListener {
            val navegar = Navigation.findNavController(view)
            navegar.navigate(R.id.action_asteroidesFragment_to_menuFragment)
        }

        viewModel.obterLista().observe(viewLifecycleOwner) {
            _lista.addAll(it)
            recylerAdapter.notifyDataSetChanged()
        }

    }



}