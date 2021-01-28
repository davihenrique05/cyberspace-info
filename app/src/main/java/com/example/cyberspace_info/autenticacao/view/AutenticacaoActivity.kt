package com.example.cyberspace_info.autenticacao.view

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.cyberspace_info.R
import com.example.cyberspace_info.autenticacao.adapter.INavegarTab
import com.example.cyberspace_info.autenticacao.adapter.LoginAdapter
import com.example.cyberspace_info.autenticacao.login.view.LoginFragment
import com.example.cyberspace_info.autenticacao.registro.view.SignFragment
import com.example.cyberspace_info.utils.NetworkListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout

const val LOGIN_FRAGMENT = 0

class AutenticacaoActivity : AppCompatActivity(), INavegarTab {

    private val tab by lazy { findViewById<TabLayout>(R.id.tabLayoutLogin) }
    lateinit var loginFragment: LoginFragment

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autenticacao)


        checkConnection()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkConnection() {
        var isOnline = NetworkListener.isOnline(this)
        if (!isOnline) {
            val dialog = MaterialAlertDialogBuilder(this,R.style.MyDialogTheme)
                .setCancelable(false)
                .setTitle(getString(R.string.erro_tittle))
                .setMessage(getString(R.string.internet_erro_content))
                .setPositiveButton(getString(R.string.internet_erro_confirmed)) { _, _ ->
                    checkConnection()
                }
                .setNegativeButton(getString(R.string.internet_erro_sair)) { _, _ ->
                    finish()
                }
                .show()

        } else {
            configurarNavegacaoEDados()
        }
    }

    private fun configurarNavegacaoEDados() {
        val pager = findViewById<ViewPager>(R.id.viewPagerLogin)
        val tab = findViewById<TabLayout>(R.id.tabLayoutLogin)

        loginFragment = LoginFragment()
        pager.adapter = LoginAdapter(
            listOf(loginFragment, SignFragment()),
            listOf("Login", "Sign"),
            supportFragmentManager
        )

        tab.setupWithViewPager(pager)
    }

    override fun mudarTab(posicaoAtual: Int) {
        val novaPosicao = LOGIN_FRAGMENT

        val tabNova = tab.getTabAt(novaPosicao)
        tabNova?.select()
    }

    override fun emailAlterado(username: String) {
        loginFragment.userNameAlterado(username)
    }

}