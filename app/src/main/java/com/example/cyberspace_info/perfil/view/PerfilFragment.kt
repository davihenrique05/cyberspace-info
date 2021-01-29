package com.example.cyberspace_info.perfil.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.example.cyberspace_info.R
import com.example.cyberspace_info.autenticacao.view.AutenticacaoActivity
import com.example.cyberspace_info.db.ImagemDatabase
import com.example.cyberspace_info.perfil.entity.ImagemEntity
import com.example.cyberspace_info.perfil.repository.ImagemRepository
import com.example.cyberspace_info.perfil.viewmodel.ImagemViewModel
import com.example.cyberspace_info.utils.NetworkListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

private const val CONTENT_REQUEST_CODE = 1

class PerfilFragment : Fragment() {

    lateinit var _viewModel: ImagemViewModel
    private var _listaDeImagens = mutableListOf<ImagemEntity>()
    private var _imageURI: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lista = arguments?.get("lista") as MutableList<HashMap<String, Any>>
        val user = FirebaseAuth.getInstance().currentUser
        _viewModel = ViewModelProvider(
            this,
            ImagemViewModel.ImagemViewModelFacytory(
                ImagemRepository(
                    ImagemDatabase.getDataBase(view.context).imagemDao()
                )
            )
        ).get(ImagemViewModel::class.java)

        val editButton = view.findViewById<ImageView>(R.id.imageIconEdit)
        editButton.setOnClickListener {
            editButton.setImageResource(R.drawable.ic_baseline_check_24)
            mudarUiParaAlteraçãoDeDados()

        }

        view.findViewById<ImageView>(R.id.imageIconReturnPerfil).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.popBackStack()
        }

        view.findViewById<ImageView>(R.id.imageIconLogOut).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(view.context, AutenticacaoActivity::class.java)
            activity?.overridePendingTransition(
                R.anim.fragment_fade_enter,
                R.anim.fragment_fade_exit
            )
            startActivity(intent)
            activity?.finish()
        }

        view.findViewById<ImageView>(R.id.imageIconGaleria).setOnClickListener {
            val bundle = bundleOf("Origem" to getString(R.string.perfil_comparacao))
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_perfilFragment_to_galeriaFragment, bundle)
        }

        _viewModel.obterImagems(user!!.uid).observe(viewLifecycleOwner) { listaLocal ->

            if (listaLocal.isNullOrEmpty()) {
                if (!lista.isNullOrEmpty()) {
                    lista.forEach { imagemFB ->
                        val imagemUrl = imagemFB["url"] as String
                        var isSaved = false

                        listaLocal.forEach { imagemLocal ->
                            if (imagemLocal.url == imagemUrl) {
                                isSaved = true
                            }
                        }

                        if (!isSaved) {
                            _viewModel.salvarImagem(imagemUrl, user!!.uid)
                                .observe(viewLifecycleOwner) {}
                        }
                    }
                }
            } else {
                armazenandoLista(listaLocal)
            }
        }

        exibirImagensPerfil()
        exibirDadosDoUsuario()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun exibirDadosDoUsuario() {
        val user = FirebaseAuth.getInstance().currentUser
        if (!user!!.displayName.isNullOrBlank()) {
            val nome = requireView().findViewById<TextView>(R.id.txtPerfilNome)
            nome.text = user.displayName
        } else {
            requireView().findViewById<TextView>(R.id.txtPerfilNome).text = "Guest"
        }

        val storage = FirebaseStorage.getInstance()
        val ref = storage.getReference("usersprofile")
        val imagemProfile = requireView().findViewById<CircleImageView>(R.id.imgPerfil)
        ref.child(user.uid).downloadUrl
            .addOnSuccessListener {
                Picasso.get()
                    .load(it)
                    .into(imagemProfile)
            }
            .addOnFailureListener {
                imagemProfile.setImageResource(R.drawable.gorgeuos_space_cat)
            }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun mudarUiParaAlteraçãoDeDados() {
        val nome = requireView().findViewById<TextView>(R.id.txtPerfilNome)
        val edtNome = requireView().findViewById<EditText>(R.id.edtPerfilNome)
        val uploadImage = requireView().findViewById<ImageView>(R.id.imgPerfilEdit)

        requireView().findViewById<CircleImageView>(R.id.circleBackPerfilEdit).visibility =
            View.VISIBLE
        uploadImage.visibility = View.VISIBLE
        edtNome.visibility = View.VISIBLE
        edtNome.setText(nome.text)
        nome.visibility = View.GONE

        uploadImage.setOnClickListener {
            procurarArquivo()
        }

        val editButton = requireView().findViewById<ImageView>(R.id.imageIconEdit)
        editButton.setOnClickListener {
            atualizarInfo()

        }
    }

    fun procurarArquivo() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, CONTENT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CONTENT_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            _imageURI = data?.data
            val imagemProfile = requireView().findViewById<CircleImageView>(R.id.imgPerfil)
            atribuirImagem(imagemProfile, _imageURI!!)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun atualizarInfo() {
        val name = requireView().findViewById<EditText>(R.id.edtPerfilNome).text.toString()

        if (_imageURI != null) {
            if (!name.isNullOrBlank()) {
                if (NetworkListener.isOnline(requireContext())) {
                    val user = FirebaseAuth.getInstance().currentUser

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    user!!.updateProfile(profileUpdates).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val imagemProfile =
                                requireView().findViewById<CircleImageView>(R.id.imgPerfil)
                            armazenarImagemNoStorage(imagemProfile)
                            alterarUiPadrao()
                        }
                    }
                }
            }
        }
    }

    private fun alterarUiPadrao() {
        val nome = requireView().findViewById<TextView>(R.id.txtPerfilNome)
        val edtNome = requireView().findViewById<EditText>(R.id.edtPerfilNome)
        val uploadImage = requireView().findViewById<ImageView>(R.id.imgPerfilEdit)
        val editButton = requireView().findViewById<ImageView>(R.id.imageIconEdit)

        nome.text = edtNome.text
        editButton.visibility = View.GONE
        requireView().findViewById<CircleImageView>(R.id.circleBackPerfilEdit).visibility =
            View.GONE
        uploadImage.visibility = View.GONE
        edtNome.visibility = View.GONE
        nome.visibility = View.VISIBLE

    }

    private fun armazenarImagemNoStorage(imagemProfile: CircleImageView) {

        _imageURI?.run {
            val user = FirebaseAuth.getInstance().currentUser
            val firebase = FirebaseStorage.getInstance()
            val storage = firebase.getReference("usersprofile")

            val fileName = user!!.uid
            val fileReference = storage.child(fileName)

            fileReference.putFile(this)
                .addOnSuccessListener {
                    Toast.makeText(
                        requireContext(),
                        "Arquivo enviado com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun exibirImagensPerfil() {
        val user = FirebaseAuth.getInstance().currentUser
        val imagem = requireView().findViewById<ImageView>(R.id.imageGalery1)
        val imagem2 = requireView().findViewById<ImageView>(R.id.imageGalery2)
        val imagem3 = requireView().findViewById<ImageView>(R.id.imageGalery3)
        val backView = requireView().findViewById<View>(R.id.viewListaVazia)
        val mensagem = requireView().findViewById<TextView>(R.id.txtListaVazia)

        _viewModel.obterImagems(user!!.uid).observe(viewLifecycleOwner) {
            _listaDeImagens.clear()
            _listaDeImagens.addAll(it)

            if (_listaDeImagens.isEmpty()) {
                backView.visibility = View.VISIBLE
                mensagem.visibility = View.VISIBLE
            } else {
                backView.visibility = View.GONE
                mensagem.visibility = View.GONE
                var urls = mutableListOf<String>()
                val ultimaPosicao = _listaDeImagens.size - 1

                for (i in ultimaPosicao downTo 0) {
                    urls.add(_listaDeImagens[i].url)
                }

                for (i in urls) {
                    var posicao = urls.indexOf(i)

                    if (posicao == 0) {
                        atribuirImagem(imagem, i)
                        abrirImagem(imagem, i)

                    } else if (posicao == 1) {
                        atribuirImagem(imagem2, i)
                        abrirImagem(imagem2, i)

                    } else if (posicao == 2) {
                        atribuirImagem(imagem3, i)
                        abrirImagem(imagem3, i)

                    } else {
                        break
                    }
                }
            }
        }
    }

    fun atribuirImagem(imagem: ImageView, url: String) {
        Picasso.get()
            .load(url)
            .into(imagem)
    }

    fun atribuirImagem(imagem: CircleImageView, url: Uri) {
        Picasso.get()
            .load(url)
            .into(imagem)
    }

    fun abrirImagem(imagem: ImageView, url: String) {
        imagem.setOnClickListener {
            val bundle = bundleOf("Imagem" to url)
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_perfilFragment_to_imagemFragment, bundle)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun armazenandoLista(lista: MutableList<ImagemEntity>) {

        if (NetworkListener.isOnline(requireContext())) {
            val user = FirebaseAuth.getInstance().currentUser
            val db = FirebaseDatabase.getInstance()
            val ref = db.getReference(user!!.uid)

            ref.setValue(lista)
        }
    }

}