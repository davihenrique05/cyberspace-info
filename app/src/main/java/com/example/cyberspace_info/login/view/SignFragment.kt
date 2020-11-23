package com.example.cyberspace_info.login.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.example.cyberspace_info.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText

class SignFragment : Fragment() {

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

            if(verificarCampos(email,senha,confirmacao)) {

            }
        }
    }

    private fun verificarCampos(email: TextInputEditText?,
                                pass: TextInputEditText?,
                                passConfirmed: TextInputEditText?
    ): Boolean {

        if (email?.text.toString() == "") {
            email?.error = "O campo e-mail não pode estar vazio"
            email?.requestFocus()
            return false
        }
        if (pass?.text.toString() == "") {
            pass?.error = "O campo senha não pode estar vazio"
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
}