package com.example.cyberspace_info.menu.view

import android.app.Activity
import android.os.Build
import android.os.Bundle
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
import com.example.cyberspace_info.utils.NetworkListener
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso

class MenuFragment : Fragment() {

    lateinit var _viewModel: ImageViewModel

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

        _viewModel = ViewModelProvider(
            this, ImageViewModel.ImageViewModelFactory(
                ImageModelRepository()
            )
        ).get(ImageViewModel::class.java)


        view.findViewById<MaterialCardView>(R.id.cardPerfil).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_menuFragment_to_perfilFragment)
        }

        checkConnection(requireActivity())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkConnection(activity: Activity) {
        var isOnline = NetworkListener.isOnline(requireContext())
        if (!isOnline) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(activity.getString(R.string.erro_tittle))
                .setMessage(activity.getString(R.string.internet_erro_content))
                .setPositiveButton(activity.getString(R.string.internet_erro_confirmed)) { _, _ ->
                    checkConnection(activity)
                }
                .setNegativeButton(activity.getString(R.string.internet_erro_sair)) { _, _ ->
                    requireActivity().finish()
                }
                .show()

        } else {
            configurarNavegacaoEDados()
        }
    }

    fun configurarNavegacaoEDados() {
        var url = ""

        val title = requireView().findViewById<TextView>(R.id.txtImagemTittle)
        val image = requireView().findViewById<ImageView>(R.id.imgImagemDoDia)

        _viewModel.obterImagemDoDia().observe(viewLifecycleOwner) {
            title.text = it.tittle
            url = it.url
            Picasso.get()
                .load(it.url)
                .into(image)
        }

        requireView().findViewById<MaterialCardView>(R.id.cardTempoEmMarte).setOnClickListener {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_menuFragment_to_tempoEmMarteFragment)
        }

        requireView().findViewById<MaterialCardView>(R.id.cardNovasTecnologias).setOnClickListener {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_menuFragment_to_tecnologiasUsadasFragment)
        }

        requireView().findViewById<MaterialCardView>(R.id.cardMarsRover).setOnClickListener {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_menuFragment_to_marsRoverFragment)
        }

        requireView().findViewById<MaterialCardView>(R.id.cardImagensEVideos).setOnClickListener {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_menuFragment_to_pesquisaFragment)
        }


        requireView().findViewById<MaterialCardView>(R.id.cardEventosNaturais).setOnClickListener {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_menuFragment_to_eventosNaturaisFragment)
        }

        requireView().findViewById<MaterialCardView>(R.id.cardImagemDoDia).setOnClickListener {
            val bundle =
                bundleOf("Tela" to getString(R.string.menu_comparacao), "Imagem" to url)
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_menuFragment_to_imagemFragment, bundle)
        }

        requireView().findViewById<MaterialCardView>(R.id.cardObjetosEmColisao).setOnClickListener {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_menuFragment_to_asteroidesFragment)
        }
    }
}