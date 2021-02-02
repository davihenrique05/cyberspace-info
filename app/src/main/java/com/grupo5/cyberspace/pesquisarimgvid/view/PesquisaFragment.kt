package com.grupo5.cyberspace.pesquisarimgvid.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.grupo5.cyberspace.R


class PesquisaFragment : Fragment() {

    private lateinit var _navigation: NavController

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
        val search = requireView().findViewById<TextView>(R.id.txtPesquisaImage)

        search.setOnEditorActionListener { _, action, event ->
            if(action == EditorInfo.IME_ACTION_SEARCH)
            {
                mudarTela(search.text.toString())
            }
            return@setOnEditorActionListener true
        }

        requireView().findViewById<ImageView>(R.id.imgBtnPesquisar).setOnClickListener {
            mudarTela(search.text.toString())
        }
    }

    private fun mudarTela(search:String){
        val bundle = bundleOf("search" to search)
        view?.hideKeyboard()
        _navigation.navigate(R.id.action_pesquisaFragment_to_resultadoPesquisaFragment, bundle)
    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}