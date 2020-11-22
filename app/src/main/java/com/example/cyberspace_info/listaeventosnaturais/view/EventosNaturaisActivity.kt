package com.example.cyberspace_info.listaeventosnaturais.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listaeventosnaturais.view.adapter.EventoAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout

class EventosNaturaisActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos_naturais)

        setupBackButton()

        var tab = findViewById<TabLayout>(R.id.layoutLogin)
        var page = findViewById<ViewPager>(R.id.viewPagerLogin)
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

    fun setupBackButton() {

        getSupportActionBar()?.setDisplayShowTitleEnabled(false);

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}