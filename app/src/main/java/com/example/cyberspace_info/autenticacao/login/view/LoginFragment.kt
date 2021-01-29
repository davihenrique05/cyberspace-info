package com.example.cyberspace_info.autenticacao.login.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cyberspace_info.R
import com.example.cyberspace_info.menu.view.MainActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

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
        val senhaContainer = view.findViewById<TextInputLayout>(R.id.txtInputSenhaLogin)

        view.findViewById<Button>(R.id.btnLogin).setOnClickListener {

            if (verificarCampos(email, senha)) {
                realizarLogin(email.text.toString(), senha.text.toString())
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

    private fun realizarLogin(emailText: String, passText: String) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(emailText, passText)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    requireActivity().overridePendingTransition(
                        R.anim.fragment_fade_enter,
                        R.anim.fragment_fade_exit
                    )
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    Toast.makeText(requireContext(), "Credenciais incorretas", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

}