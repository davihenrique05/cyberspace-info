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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
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
    private var currentItens = 0
    private var totalItens = 0
    private var scrollOutItens = 0
    var lastItemCompare = 0

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
            TOTAL_INITAL_ITENS_IMAGE = 0
            navigation.popBackStack()

        }
        if(!search.isNullOrEmpty()){
            requireView().findViewById<TextInputEditText>(R.id.txtpesquisaimagefragment).setText(search)
            requireView().findViewById<TextInputEditText>(R.id.txtpesquisaimagefragment).clearFocus()
        }
        novaPesquisa()
    }

    private fun novaPesquisa() {

        val search = requireView().findViewById<TextInputEditText>(R.id.txtpesquisaimagefragment)
        search.setOnEditorActionListener { _, action, event ->
            if(action == EditorInfo.IME_ACTION_SEARCH)
            {
                mudarTela(search.text.toString())
            }
            return@setOnEditorActionListener true
        }
        requireView().findViewById<ImageView>(R.id.imgViewProcurarPesquisaImgVid).setOnClickListener {
            mudarTela(search.text.toString())
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

        for (i in START_VALUE_LIST_IMAGE..INDEX_START_VALUE_IMAGE) {
            _listaImagens.add(lista[i])
            _adaptador.notifyDataSetChanged()
        }

        if(CLICK_SEARCH == false){
        TOTAL_INITAL_ITENS_IMAGE = TOTAL_INITAL_ITENS_IMAGE + ITENS_AFTER_UPDATE_IMAGE
}

        showLoading(false)

        var firstVisible = _viewManager.findFirstVisibleItemPosition()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val currentFirstVisible = _viewManager.findFirstVisibleItemPosition()

                firstVisible = currentFirstVisible

                val beforeScrollTotal = START_VALUE_LIST_IMAGE

                currentItens = _viewManager.childCount
                totalItens = _viewManager.itemCount
                scrollOutItens = _viewManager.findFirstVisibleItemPosition()

                pushDinamicItensWithSleep(dy,beforeScrollTotal,lista)

            }

        })

    }

    private fun pushDinamicItensWithSleep(dy:Int,beforeScrollTotal:Int,lista: List<ObjectImageModel>){

        val lastItemCurrent = _viewManager.findLastVisibleItemPosition()

        if(!(dy < START_VALUE_LIST_IMAGE && scrollOutItens >= START_VALUE_LIST_IMAGE && beforeScrollTotal == START_VALUE_LIST_IMAGE)){

            if(lastItemCurrent >= lastItemCompare){

                lastItemCompare = lastItemCurrent

                if((lastItemCompare <=  lista.size) && (lastItemCompare) >= (TOTAL_INITAL_ITENS_IMAGE)){

                    SystemClock.sleep(1500)

                    for (i in (lastItemCompare)..(lastItemCompare+ITENS_AFTER_UPDATE_IMAGE)) {

                        if(i <= lista.size-1) {
                            _listaImagens.add(lista[i])
                        }
                    }
                    TOTAL_INITAL_ITENS_IMAGE = TOTAL_INITAL_ITENS_IMAGE + ITENS_AFTER_UPDATE_IMAGE
                }
            }

        }

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
        lastItemCompare = 0
        CLICK_SEARCH = true
        TOTAL_INITAL_ITENS_IMAGE = 0
        resultarPesquisa(search)
    }

    companion object{
        var CLICK_SEARCH = false
        var TOTAL_INITAL_ITENS_IMAGE = 0
        var ITENS_AFTER_UPDATE_IMAGE = 25
        var START_VALUE_LIST_IMAGE = 0
        var INDEX_START_VALUE_IMAGE = 25
    }

}
