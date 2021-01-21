package com.example.cyberspace_info.pesquisarimgvid.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.cyberspace_info.R


class PesquisaFragment : Fragment() {

    lateinit var _navigation: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pesquisa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _navigation = Navigation.findNavController(view)

        view.findViewById<ImageView>(R.id.imgViewMenuPesquisa).setOnClickListener {
            _navigation.popBackStack()
        }

        pesquisar()
    }

    private fun pesquisar() {

        requireView().findViewById<ImageView>(R.id.imgBtnPesquisar).setOnClickListener {

            var search = requireView().findViewById<TextView>(R.id.txtPesquisaImage).text.toString()
            val bundle = bundleOf("search" to search)
            _navigation.navigate(R.id.action_pesquisaFragment_to_resultadoPesquisaFragment,bundle)
        }
    }
}