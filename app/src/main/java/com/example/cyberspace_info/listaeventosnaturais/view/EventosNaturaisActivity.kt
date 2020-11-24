package com.example.cyberspace_info.listaeventosnaturais.view

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listaeventosnaturais.view.adapter.EventoAdapter
import com.google.android.material.tabs.TabLayout


class EventosNaturaisActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos_naturais)

        var imgBack = findViewById<ImageView>(R.id.imgReturn).setOnClickListener {
            finish()
        }

        var tab = findViewById<TabLayout>(R.id.layoutNaturalEvents)
        var page = findViewById<ViewPager>(R.id.viewPagerNaturalEvents)

        setupViewPagerAndTabLayout(tab,page)

    }

    fun setupViewPagerAndTabLayout(tab : TabLayout,page: ViewPager){

        tab.setupWithViewPager(page)

        var fragmentManager = supportFragmentManager

        page.adapter = EventoAdapter(
            listOf(EventosNaturaisAtuaisFragment(), EventosNaturaisAnterioresFragment()),
            listOf(getString(R.string.atuais), getString(R.string.anteriores)),
            fragmentManager
        )
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.from_left,R.anim.to_right)
    }


}