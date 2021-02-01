package com.example.cyberspace_info.pesquisarimgvid.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.pesquisarimgvid.model.ObjectImageModel
import com.example.cyberspace_info.pesquisarimgvid.repository.PesquisarImagemRepository
import com.example.cyberspace_info.pesquisarimgvid.view.adapter.PesquisaImgAdapter
import com.example.cyberspace_info.pesquisarimgvid.viewmodel.PesquisarImagemViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText

class ResultadoPesquisaFragment : Fragment() {

    private lateinit var _viewModel: PesquisarImagemViewModel
    private lateinit var _listaImagens: MutableList<ObjectImageModel>
    private lateinit var _adaptador: PesquisaImgAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_resultado_pesquisa, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigation = Navigation.findNavController(view)
        val search = arguments?.getString("search")
        _listaImagens = mutableListOf()

        resultarPesquisa(search)
        view.findViewById<ImageView>(R.id.imgViewMenuPesquisaImgVid).setOnClickListener {
            navigation.popBackStack()
        }
        if(!search.isNullOrEmpty()){
            requireView().findViewById<TextInputEditText>(R.id.txtpesquisaimagefragment).setText(search)
            requireView().findViewById<TextInputEditText>(R.id.txtpesquisaimagefragment).clearFocus()
        }
        novaPesquisa()
    }

    private fun novaPesquisa() {
        requireView().findViewById<ImageView>(R.id.imgViewProcurarPesquisaImgVid)
            .setOnClickListener {
                val search =
                    requireView().findViewById<TextInputEditText>(R.id.txtpesquisaimagefragment).text.toString()
                _listaImagens = mutableListOf()
                view?.hideKeyboard()
                resultarPesquisa(search)
            }
    }

    private fun resultarPesquisa(search: String?) {

        val progresBar = requireView().findViewById<ProgressBar>(R.id.progessBar)
        showLoading(true)

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

        val viewManager = GridLayoutManager(requireContext(), 3)
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.listaFotosVideos)
        _adaptador = PesquisaImgAdapter(_listaImagens) {
            val navigation = Navigation.findNavController(requireView())
            val bundle = bundleOf("Imagem" to it.links[0].href)
            navigation.navigate(R.id.action_resultadoPesquisaFragment_to_imagemFragment, bundle)
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = _adaptador
        }

        if (search != null) {
            _viewModel.getUrlsImages(search).observe(viewLifecycleOwner) {
                if (!it.isNullOrEmpty()) {
                    exibirLista(it)
                } else {
                    showLoading(false)
                    requireView().findViewById<MaterialCardView>(R.id.cardNotFound).visibility =
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
        _listaImagens.addAll(lista)
        showLoading(false)
        _adaptador.notifyDataSetChanged()
    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}