package com.grupo5.cyberspace.autenticacao.login.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.menu.view.MainActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.*


class LoginFragment : Fragment() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = view.findViewById<TextInputEditText>(R.id.edtEmailLogin)
        val senha = view.findViewById<TextInputEditText>(R.id.edtSenhaLogin)

        callbackManager = CallbackManager.Factory.create()

        auth = FirebaseAuth.getInstance()

        /* variáveis para autenticação google */
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(view.context, gso)

        view.findViewById<Button>(R.id.btnLogin).setOnClickListener {
            if (verificarCampos(email, senha)) {
                realizarLogin(email.text.toString(), senha.text.toString(), "E")
            }
        }

        view.findViewById<ImageView>(R.id.btnLoginGoogle).setOnClickListener {
            realizarLogin("", "", "G")
        }

        view.findViewById<ImageView>(R.id.btnLoginFacebook).setOnClickListener {
            realizarLogin("", "", "F")
        }
        view.findViewById<Button>(R.id.esqueceu_senha).setOnClickListener {
            val resetEmail = email.text.toString()
            if(resetEmail.isNotBlank()){
                auth.fetchSignInMethodsForEmail(resetEmail).addOnCompleteListener {
                    if(it.result?.signInMethods?.size  == 0){
                        Toast.makeText(requireContext(),
                            getString(R.string.nao_cadastrado),
                            Toast.LENGTH_SHORT)
                            .show()
                    }else{
                        resetarSenha(resetEmail)
                    }
                }
            }
        }
    }

    private fun resetarSenha(resetEmail: String) {
        auth.sendPasswordResetEmail(resetEmail).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(requireContext(),
                    getString(R.string.email_enviado_com_suceso),
                    Toast.LENGTH_SHORT)
                    .show()
            }else{
                Toast.makeText(requireContext(),
                    it.exception.toString(),
                    Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    private fun verificarCampos(
        email: TextInputEditText?,
        pass: TextInputEditText?
    ): Boolean {

        if (email?.text.toString() == "") {
            email?.error = getString(R.string.email_vazio)
            email?.requestFocus()
            return false
        }

        if (pass?.text.toString() == "") {
            pass?.error = getString(R.string.senha_vazia)
            pass?.requestFocus()
            return false
        }

        return true
    }

    fun userNameAlterado(username: String) {
        val email = view?.findViewById<TextInputEditText>(R.id.edtEmailLogin)
        email?.setText(username)
        val pass = view?.findViewById<TextInputEditText>(R.id.edtSenhaLogin)
        pass?.requestFocus()
    }

    private fun realizarLogin(emailText: String, passText: String, tipoLogin: String) {
        when (tipoLogin) {
            // autenticação por email
            "E" -> {
                auth.signInWithEmailAndPassword(emailText, passText)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            openMain()
                        }else{
                            erroCredencial()
                        }
                    }
            }

            // autenticação por gmail
            "G" -> {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }

            // autenticação por facebook
            "F" -> {
                loginFacebook()
            }
        }
    }

    fun openMain() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        requireActivity().overridePendingTransition(
            R.anim.fragment_fade_enter,
            R.anim.fragment_fade_exit
        )
        startActivity(intent)
        requireActivity().finish()
    }

    private fun erroCredencial() {
        Toast.makeText(
            requireContext(),
            getString(R.string.dados_incorretos),
            Toast.LENGTH_SHORT
        )
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("GoogleAuth", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                erroCredencial()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    openMain()
                } else {
                    erroCredencial()
                }
            }
    }

    private fun loginFacebook() {
        val instanceFirebase = LoginManager.getInstance()

        instanceFirebase.logInWithReadPermissions(this, listOf("email", "public_profile"))
        instanceFirebase.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                val credential: AuthCredential =
                    FacebookAuthProvider.getCredential(loginResult.accessToken.token)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { openMain() }
            }

            override fun onCancel() {
                Toast.makeText(requireContext(), getString(R.string.canceled), Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(requireContext(), getString(R.string.ocorreu_um_erro), Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private const val RC_SIGN_IN = 1
    }
}