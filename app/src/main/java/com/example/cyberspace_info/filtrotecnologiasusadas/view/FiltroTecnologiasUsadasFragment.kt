package com.example.cyberspace_info.filtrotecnologiasusadas.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cyberspace_info.R


class FiltroTecnologiasUsadasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filtro_tecnologias_usadas, container, false)
    }


}