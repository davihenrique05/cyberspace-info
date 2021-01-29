package com.example.cyberspace_info.menu.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.example.cyberspace_info.R
import com.example.cyberspace_info.menu.imagemdodia.repository.ImageModelRepository
import com.example.cyberspace_info.menu.imagemdodia.viewmodel.ImageViewModel
import com.example.cyberspace_info.perfil.entity.ImagemEntity
import com.example.cyberspace_info.utils.NetworkListener
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso


class MenuFragment : Fragment() {

    lateinit var _viewModel: ImageViewModel
    lateinit var _url: String
    private var _listaImagens = arrayListOf<ImagemEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = requireView().findViewById<TextView>(R.id.txtImagemTittle)
        val image = requireView().findViewById<ImageView>(R.id.imgImagemDoDia)

        _viewModel = ViewModelProvider(
            this, ImageViewModel.ImageViewModelFactory(
                ImageModelRepository()
            )
        ).get(ImageViewModel::class.java)


        configurarNavegacaoDoMenu()

        if (NetworkListener.isOnline(requireContext())) {
            requisicaoImagemDoDia(title, image)
        } else {
            image.setBackgroundColor(requireActivity().getColor(R.color.colorPrimaryDarkestMenu))
        }

        armazenandoLista()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun configurarNavegacaoDoMenu() {

        requireView().findViewById<MaterialCardView>(R.id.cardPerfil).setOnClickListener {
            val navControl = Navigation.findNavController(requireView())
            val bundle = bundleOf("lista" to _listaImagens)
            navControl.navigate(R.id.action_menuFragment_to_perfilFragment, bundle)
        }

        requireView().findViewById<MaterialCardView>(R.id.cardTempoEmMarte).setOnClickListener {
            definirDestino(R.id.action_menuFragment_to_tempoEmMarteFragment)
        }

        requireView().findViewById<MaterialCardView>(R.id.cardNovasTecnologias).setOnClickListener {
            definirDestino(R.id.action_menuFragment_to_tecnologiasUsadasFragment)
        }

        requireView().findViewById<MaterialCardView>(R.id.cardMarsRover).setOnClickListener {
            definirDestino(R.id.action_menuFragment_to_marsRoverFragment)
        }

        requireView().findViewById<MaterialCardView>(R.id.cardImagensEVideos).setOnClickListener {
            definirDestino(R.id.action_menuFragment_to_pesquisaFragment)
        }

        requireView().findViewById<MaterialCardView>(R.id.cardEventosNaturais).setOnClickListener {
            definirDestino(R.id.action_menuFragment_to_eventosNaturaisFragment)
        }

        requireView().findViewById<MaterialCardView>(R.id.cardImagemDoDia).setOnClickListener {
            definirDestino(R.id.action_menuFragment_to_imagemFragment)
        }

        requireView().findViewById<MaterialCardView>(R.id.cardObjetosEmColisao).setOnClickListener {
            definirDestino(R.id.action_menuFragment_to_asteroidesFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun definirDestino(navDestino: Int) {
        val online = NetworkListener.isOnline(requireContext())
        val navController = Navigation.findNavController(requireView())

        if (navDestino != R.id.action_menuFragment_to_imagemFragment) {
            Log.e("Url", online.toString())
            if (online) {
                navController.navigate(navDestino)
            } else {
                navController.navigate(R.id.action_menuFragment_to_erroFragment)
            }
        } else {
            if (online) {
                val bundle =
                    bundleOf("Tela" to getString(R.string.menu_comparacao), "Imagem" to _url)
                navController.navigate(navDestino, bundle)
            } else {

                navController.navigate(R.id.action_menuFragment_to_erroFragment)
            }
        }

    }

    private fun requisicaoImagemDoDia(title: TextView, image: ImageView) {
        _viewModel.obterImagemDoDia().observe(viewLifecycleOwner) {
            title.text = it.tittle
            _url = it.url
            Picasso.get()
                .load(it.url)
                .into(image)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun armazenandoLista() {

        if (NetworkListener.isOnline(requireContext())) {
            val user = FirebaseAuth.getInstance().currentUser
            val db = FirebaseDatabase.getInstance()
            val ref = db.getReference(user!!.uid)

            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val lista = snapshot.value as ArrayList<ImagemEntity>?

                    if (lista != null) {
                        _listaImagens = lista
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }

}