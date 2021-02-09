package com.grupo5.cyberspace.pesquisarimgvid.view

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.pesquisarimgvid.model.ObjectImageModel
import com.grupo5.cyberspace.pesquisarimgvid.repository.PesquisarImagemRepository
import com.grupo5.cyberspace.pesquisarimgvid.view.adapter.PesquisaImgAdapter
import com.grupo5.cyberspace.pesquisarimgvid.viewmodel.PesquisarImagemViewModel

class ResultadoPesquisaFragment : Fragment() {

    private lateinit var _viewModel: PesquisarImagemViewModel
    private lateinit var _listaImagens: MutableList<ObjectImageModel>
    private lateinit var _adaptador: PesquisaImgAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var _viewManager : GridLayoutManager
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_resultado_pesquisa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigation = Navigation.findNavController(view)
        if(_search.isBlank()){
            _search = arguments?.getString("search").toString()
        }
        _listaImagens = mutableListOf()
        resultarPesquisa(_search)
        view.findViewById<ImageView>(R.id.imgViewMenuPesquisaImgVid).setOnClickListener {
            navigation.popBackStack()

        }
        if(!_search.isNullOrEmpty()){
            requireView().findViewById<TextInputEditText>(R.id.txtpesquisaimagefragment).setText(_search)
            requireView().findViewById<TextInputEditText>(R.id.txtpesquisaimagefragment).clearFocus()
        }
        novaPesquisa()
    }

    private fun novaPesquisa() {

        val search = requireView().findViewById<TextInputEditText>(R.id.txtpesquisaimagefragment)
        search.setOnEditorActionListener { _, action, event ->
            if(action == EditorInfo.IME_ACTION_SEARCH)
            {
                _search = search.text.toString()
                mudarTela(_search)
            }
            return@setOnEditorActionListener true
        }
        requireView().findViewById<ImageView>(R.id.imgViewProcurarPesquisaImgVid).setOnClickListener {
            _search = search.text.toString()
            mudarTela(_search)
        }
    }

    private fun resultarPesquisa(search: String?) {

        val progresBar = requireView().findViewById<ProgressBar>(R.id.progessBar)
        showLoading(true)

        requireView().findViewById<ConstraintLayout>(R.id.cardNotFound).visibility =
            View.GONE

        val color = ContextCompat.getColor(requireContext(), R.color.colorWhite)
        @Suppress("DEPRECATION")
        progresBar.indeterminateDrawable.setColorFilter(
            color,
            android.graphics.PorterDuff.Mode.MULTIPLY
        )

        _viewModel = ViewModelProvider(
            this, PesquisarImagemViewModel.PesquisarImagemViewModelFactory(
                PesquisarImagemRepository()
            )
        ).get(PesquisarImagemViewModel::class.java)

        _viewManager = GridLayoutManager(requireContext(), 3)
        recyclerView = requireView().findViewById<RecyclerView>(R.id.listaFotosVideos)
        _adaptador = PesquisaImgAdapter(_listaImagens) {
            val navigation = Navigation.findNavController(requireView())
            val bundle = bundleOf("Imagem" to it.links[0].href)
            navigation.navigate(R.id.action_resultadoPesquisaFragment_to_imagemFragment, bundle)
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = _viewManager
            adapter = _adaptador
        }

        if (search != null) {
            _viewModel.getUrlsImages(search).observe(viewLifecycleOwner) {
                if (!it.isNullOrEmpty()) {
                    exibirLista(it)
                } else {
                    showLoading(false)
                    requireView().findViewById<ConstraintLayout>(R.id.cardNotFound).visibility =
                        View.VISIBLE
                }
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = requireView().findViewById<View>(R.id.loading)

        if (isLoading) {
            viewLoading?.visibility = View.VISIBLE
        } else {
            viewLoading?.visibility = View.GONE
        }
    }


    private fun exibirLista(lista: List<ObjectImageModel>) {

        showLoading(false)
        _listaImagens.clear()
        _listaImagens.addAll(lista)
        _adaptador.notifyDataSetChanged()
    }


    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun mudarTela(search:String){
        view?.hideKeyboard()
        _listaImagens = mutableListOf()
        resultarPesquisa(search)
    }

    companion object{
        var _search = ""
    }

}