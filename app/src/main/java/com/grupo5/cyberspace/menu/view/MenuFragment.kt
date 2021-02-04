package com.grupo5.cyberspace.menu.view

import android.content.Intent
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
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.menu.imagemdodia.repository.ImageModelRepository
import com.grupo5.cyberspace.menu.imagemdodia.viewmodel.ImageViewModel
import com.grupo5.cyberspace.perfil.entity.ImagemEntity
import com.grupo5.cyberspace.utils.NetworkListener
import com.google.android.material.card.MaterialCardView
import com.google.android.youtube.player.YouTubeThumbnailView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.grupo5.cyberspace.playvideo.YoutubePlayActivity
import com.grupo5.cyberspace.playvideo.YoutubeManager
import com.squareup.picasso.Picasso
import kotlinx.coroutines.android.awaitFrame


class MenuFragment : Fragment() {

    private lateinit var _viewModel: ImageViewModel
    private var _url = ""
    private var _midia = ""
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
            image.setImageResource(R.drawable.space_cats)
            title.text = getString(R.string.sem_internet)
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
                when {
                    _midia == "image" -> {
                        val bundle =
                            bundleOf("Tela" to getString(R.string.menu_comparacao), "Imagem" to _url)
                        navController.navigate(navDestino, bundle)
                    }
                    _midia.isNotEmpty() -> {
                        val intent = Intent(requireContext(),YoutubePlayActivity::class.java)
                        intent.putExtra("url", _url)
                        startActivity(intent)
                    }
                    else -> {
                        navController.navigate(R.id.action_menuFragment_to_erroFragment)
                    }
                }

            } else {
                navController.navigate(R.id.action_menuFragment_to_erroFragment)
            }
        }

    }

    private fun requisicaoImagemDoDia(title: TextView, image: ImageView) {

        _viewModel.obterImagemDoDia().observe(viewLifecycleOwner) {
            _midia = it.midia
            title.text = it.tittle
            _url = it.url

            if(it.midia == "image"){
                Picasso.get()
                    .load(it.url)
                    .into(image)
            }else{
                val thumbnail = requireView().findViewById<YouTubeThumbnailView>(R.id.imgThumbnail)
                thumbnail.visibility = View.VISIBLE
                image.visibility = View.GONE
                YoutubeManager.setThumbnail(thumbnail, it.url, getString(R.string.yotubeAPI))
            }

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
                    Log.e("Requisicao FB", error.message)
                }

            })
        }
    }

}