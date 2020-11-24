package com.example.cyberspace_info.listatecnologiasusadas.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listatecnologiasusadas.model.ProjectModel
import com.example.cyberspace_info.listatecnologiasusadas.view.adapter.TecnologiasUsadasAdapter


class TecnologiasUsadasFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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

            adapter = TecnologiasUsadasAdapter(listOf(

                ProjectModel(1,"Ultra-high Energy Particle Astrophysics with ANITA-V - Washington University - Co-I",
                    "Completed","14 jan ","24 Out","..."),
                ProjectModel(1,"Ultra-high Energy Particle Astrophysics with ANITA-V - Washington University - Co-I",
                    "Completed","14 jan ","24 Out","..."),
                ProjectModel(1,"Ultra-high Energy Particle Astrophysics with ANITA-V - Washington University - Co-I",
                    "Completed","14 jan ","24 Out","..."),
                ProjectModel(1,"Ultra-high Energy Particle Astrophysics with ANITA-V - Washington University - Co-I",
                    "Completed","14 jan ","24 Out","..."),
                ProjectModel(1,"Ultra-high Energy Particle Astrophysics with ANITA-V - Washington University - Co-I",
                    "Completed","14 jan ","24 Out","..."),
                ProjectModel(1,"Ultra-high Energy Particle Astrophysics with ANITA-V - Washington University - Co-I",
                    "Completed","14 jan ","24 Out","..."),
                ProjectModel(1,"Ultra-high Energy Particle Astrophysics with ANITA-V - Washington University - Co-I",
                    "Completed","14 jan ","24 Out","...")
            )){

                val bottomSheetFragment =  BottomSheetFragment();

                bottomSheetFragment.show((activity as AppCompatActivity).supportFragmentManager,"BottomSheetDialog")

            }

        }

    }


}