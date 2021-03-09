package com.grupo5.cyberspace.perfil.imagelandscape.view

import android.Manifest
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.db.ImagemDatabase
import com.grupo5.cyberspace.perfil.entity.ImagemEntity
import com.grupo5.cyberspace.perfil.repository.ImagemRepository
import com.grupo5.cyberspace.perfil.viewmodel.ImagemViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso


class ImagemFragment : Fragment() {

    private lateinit var _viewModel: ImagemViewModel
    private var _baixada = false
    private var _verificar = false
    private var _listaDeImagens = mutableListOf<ImagemEntity>()
    private val _requestCode = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_imagem, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = FirebaseAuth.getInstance().currentUser

        @Suppress("DEPRECATION")
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val imagemUrl = arguments?.getString("Imagem")
        val downloadBtn = view.findViewById<ImageView>(R.id.imgDownload)
        val imagem = view.findViewById<ImageView>(R.id.imageViewShow)

        if (imagemUrl != null) {
            Picasso.get()
                .load(imagemUrl)
                .into(imagem)
            compartilharImagem(imagemUrl)
        }

        _viewModel = ViewModelProvider(
            this,
            ImagemViewModel.ImagemViewModelFacytory(
                ImagemRepository(
                    ImagemDatabase.getDataBase(view.context).imagemDao()
                )
            )
        ).get(ImagemViewModel::class.java)


        _viewModel.obterImagems(user!!.uid).observe(viewLifecycleOwner) {
            _listaDeImagens.clear()
            _listaDeImagens.addAll(it)

            verificarImagem(imagemUrl,it)
        }

        downloadBtn.setOnClickListener {
            if(!_baixada){
                val array = arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )

                if(ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(
                        requireActivity(), array, _requestCode
                    )
                }else{
                    download(imagemUrl!!)
                }
            }else{
                val toast = Toast.makeText(
                    requireView().context,
                    "A imagem já foi baixada",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }

        }

        mostrarToolbar(imagem)
        fecharTela()
    }

    private fun compartilharImagem(imgemUrl: String) {
        val shareBtn = requireView().findViewById<ImageView>(R.id.imgShare)
        shareBtn.setOnClickListener {
            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Veja, encontrei uma imagem bonita: $imgemUrl")
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Compartilhar com:"))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }

    private fun favoritarImagem(url: String?) {
        val user = FirebaseAuth.getInstance().currentUser
        if (url != null) {
            _viewModel.salvarImagem(url, user!!.uid).observe(viewLifecycleOwner) {
                val toast = Toast.makeText(
                    requireContext(),
                    getString(R.string.favorito),
                    Toast.LENGTH_SHORT
                )
                toast.show()
                _verificar = true
            }
        }
    }

    private fun verificarImagem(urlImagem: String?, lista: MutableList<ImagemEntity>){
        val icone = requireView().findViewById<ImageView>(R.id.imageIconFavorite)

        for(i in lista){
            if(i.url == urlImagem){
                _verificar = true
                break
            }else{
                _verificar = false
            }
        }

        definirIcone(icone, _verificar)
        icone?.setOnClickListener {

            if (_verificar) {
                criarDialog(urlImagem, icone)
            } else {
                definirIcone(icone, true)
                favoritarImagem(urlImagem)
            }

        }
    }

    private fun criarDialog(urlImagem: String?, icone: ImageView){
        AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)
            .apply {
                setTitle(getString(R.string.remover_img))
                setMessage(getString(R.string.text_remover_fav))

                setPositiveButton("Sim") { _, _ ->
                    removerImagem(urlImagem)
                    _verificar = false
                    definirIcone(icone, false)
                }

                setNegativeButton("Não") { _: DialogInterface, _: Int -> }
            }
            .create()
            .show()
    }
    private fun removerImagem(urlImagem: String?) {

        val user = FirebaseAuth.getInstance().currentUser
        if (urlImagem != null) {
            _viewModel.deletarImagem(urlImagem, user!!.uid).observe(viewLifecycleOwner) {
                val db = FirebaseDatabase.getInstance()
                val ref = db.getReference(user.uid)

                ref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.value != null) {
                            val lista = snapshot.value as MutableList<HashMap<String, Any>>
                            val listaAtualizada = mutableListOf<ImagemEntity>()
                            lista.forEach { imagemFB ->
                                val id = imagemFB["id"] as Long
                                val uid = imagemFB["uid"] as String
                                val imagemUrl = imagemFB["url"] as String
                                if (urlImagem != imagemUrl) {
                                    val imagem = ImagemEntity(id.toInt(), uid, imagemUrl)
                                    listaAtualizada.add(imagem)
                                }
                            }
                            ref.setValue(listaAtualizada)
                            ref.removeEventListener(this)
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        val toast = Toast.makeText(
                            requireView().context,
                            getString(R.string.internet_erro_content),
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }

                })

                val toast = Toast.makeText(
                    requireView().context,
                    getString(R.string.removido),
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }

    private fun mostrarToolbar(imagem: ImageView) {
        var visivel = false
        imagem.setOnClickListener {
            if (!visivel) {
                val toolbar = requireView().findViewById<ConstraintLayout>(R.id.toolbarImagemFrag)
                toolbar.visibility = View.VISIBLE
                visivel = true
            } else {
                val toolbar = requireView().findViewById<ConstraintLayout>(R.id.toolbarImagemFrag)
                toolbar.visibility = View.GONE
                visivel = false
            }

        }
    }

    private fun definirIcone(icone: ImageView, favorito: Boolean) {
        if (favorito) {
            icone.setImageResource(R.drawable.ic_unfav)
        } else {
            icone.setImageResource(R.drawable.ic_fav)
        }
    }

    private fun fecharTela() {

        view?.findViewById<ImageView>(R.id.imageIconClose)?.setOnClickListener {
            val navController = Navigation.findNavController(requireView())
            navController.popBackStack()
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            @Suppress("DEPRECATION")
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun download(link: String) {

        val mgr: DownloadManager =
            requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadUri: Uri = Uri.parse(link)
        val request: DownloadManager.Request = DownloadManager.Request(downloadUri)

        request.setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_WIFI
                    or DownloadManager.Request.NETWORK_MOBILE
        )
            .setAllowedOverRoaming(true).setTitle("Imagem")
            .setDescription("Dowloading image")
            .setDestinationInExternalPublicDir("Download", "cyberspace-image.jpg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        mgr.enqueue(request)
        val toast = Toast.makeText(context,
            "A imagem será baixada em segundo plano",
            Toast.LENGTH_SHORT
        )
        toast.show()
        _baixada = true
    }
}