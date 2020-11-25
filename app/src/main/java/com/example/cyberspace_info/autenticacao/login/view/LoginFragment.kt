package com.example.cyberspace_info.autenticacao.login.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.cyberspace_info.R
import com.example.cyberspace_info.menu.view.MainActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
import com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE


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

            if(verificarCampos(email,senha,senhaContainer)){
                val intent = Intent(view.context,MainActivity::class.java)
                activity?.overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                startActivity(intent)
                activity?.finish()
            }
        }


    }

    private fun verificarCampos(email: TextInputEditText?, pass: TextInputEditText?, container: TextInputLayout): Boolean {

        if (email?.text.toString() == "") {
            email?.error = "O campo e-mail não pode estar vazio"
            email?.requestFocus()
            return false
        }
        if (pass?.text.toString() == "") {
            container.endIconMode = END_ICON_NONE
            pass?.error = "O campo senha não pode estar vazio"
            pass?.requestFocus()
            pass?.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    container.endIconMode = END_ICON_PASSWORD_TOGGLE
                }

                override fun afterTextChanged(s: Editable?){}

            })

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

}