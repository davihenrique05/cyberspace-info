package com.example.cyberspace_info.login.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cyberspace_info.R
import com.google.android.material.textfield.TextInputEditText

class SignFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign, container, false)
    }


    private fun verificarCampos(
        email: TextInputEditText?,
        pass: TextInputEditText?, name: TextInputEditText?,
        passConfirmed: TextInputEditText?
    ): Boolean {
        var auxiliar = mutableListOf<Boolean>(false, false, false, false)

        if (email?.text.toString() != "") auxiliar[0] = true
        if (pass?.text.toString() != "") auxiliar[1] = true
        if (name?.text.toString() != "") auxiliar[2] = true
        if (passConfirmed?.text.toString() == pass?.text.toString()) auxiliar[3] = true

        if (!auxiliar[0]) {
            email?.error = "O campo e-mail não pode estar vazio"
        }
        if (!auxiliar[1]) {
            pass?.error = "O campo senha não pode estar vazio"
        }
        if (!auxiliar[2]) {
            name?.error = "O campo name não pode estar vazio"
        }
        if (!auxiliar[3]) {
            passConfirmed?.error = "Os campos de senha não estão iguais"
        }

        if (auxiliar[0] && auxiliar[1] && auxiliar[2] && auxiliar[3]) {
            return true
        }
        return false
    }
}