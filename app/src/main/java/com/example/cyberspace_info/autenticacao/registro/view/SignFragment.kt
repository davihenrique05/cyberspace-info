package com.example.cyberspace_info.autenticacao.registro.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.cyberspace_info.R
import com.example.cyberspace_info.autenticacao.adapter.INavegarTab
import com.example.cyberspace_info.autenticacao.view.LOGIN_FRAGMENT
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignFragment : Fragment() {

    private lateinit var mudarTab: INavegarTab
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = view.findViewById<TextInputEditText>(R.id.edtEmailSign)
        val senha = view.findViewById<TextInputEditText>(R.id.edtSenhaSign)
        val confirmacao = view.findViewById<TextInputEditText>(R.id.edtSenhaConfirmacao)
        val pager = view.findViewById<ViewPager>(R.id.viewPagerLogin)
        val tab = view.findViewById<TabLayout>(R.id.tabLayoutLogin)


        view.findViewById<Button>(R.id.btnSignUP).setOnClickListener {

            if (verificarCampos(email, senha, confirmacao)) {
                criarUsuario(email.text.toString(), senha.text.toString())
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mudarTab = context as INavegarTab
    }

    private fun verificarCampos(
        email: TextInputEditText?,
        pass: TextInputEditText?,
        passConfirmed: TextInputEditText?
    ): Boolean {

        if (email?.text.toString() == "") {
            email?.error = getString(R.string.email_vazio)
            email?.requestFocus()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email?.text.toString()).matches()) {
            email!!.error = getString(R.string.email_invalido)
            return false
        }
        if (pass?.text.toString() == "") {
            pass?.error = getString(R.string.senha_vazia)
            pass?.requestFocus()
            return false
        }
        if (pass?.text.toString().length < 6) {
            pass?.error = getString(R.string.senha_pequena)
            pass?.requestFocus()
            return false
        }
        if (passConfirmed?.text.toString() != pass?.text.toString()) {
            passConfirmed?.error = "Os campos de senha não estão iguais"
            passConfirmed?.requestFocus()
            return false
        }

        return true
    }

    private fun criarUsuario(email: String, pass: String) {
        val mAuth = FirebaseAuth.getInstance()

        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Usuário criado com sucesso",
                        Toast.LENGTH_LONG
                    ).show()
                    mudarTab.mudarTab(LOGIN_FRAGMENT)
                    mudarTab.emailAlterado(email)
                } else {
                    Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
    }
}