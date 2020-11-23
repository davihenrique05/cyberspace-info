package com.example.cyberspace_info.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.cyberspace_info.R
import com.google.android.material.tabs.TabLayout

    const val LOGIN_FRAGMENT = 0

class LoginActivity : AppCompatActivity(), INavegarTab {

    private val tab by lazy { findViewById<TabLayout>(R.id.tabLayoutLogin) }
    lateinit var loginFragment: LoginFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val pager = findViewById<ViewPager>(R.id.viewPagerLogin)
        val tab = findViewById<TabLayout>(R.id.tabLayoutLogin)

        loginFragment = LoginFragment()
        pager.adapter = LoginAdapter(
            listOf( loginFragment, SignFragment()),
            listOf("Login","Sign"),
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