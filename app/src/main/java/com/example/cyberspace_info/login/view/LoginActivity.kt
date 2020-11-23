package com.example.cyberspace_info.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.cyberspace_info.R
import com.google.android.material.tabs.TabLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val pager = findViewById<ViewPager>(R.id.viewPagerLogin)
        val tab = findViewById<TabLayout>(R.id.tabLayoutLogin)

        pager.adapter = LoginAdapter(
            listOf( LoginFragment(), SignFragment()),
            listOf("Login","Sign"),
            supportFragmentManager
        )

        tab.setupWithViewPager(pager)
    }
}