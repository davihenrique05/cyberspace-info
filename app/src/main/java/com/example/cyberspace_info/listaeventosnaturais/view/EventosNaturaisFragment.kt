package com.example.cyberspace_info.listaeventosnaturais.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listaeventosnaturais.view.adapter.EventoAdapter
import com.google.android.material.tabs.TabLayout

class EventosNaturaisFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eventos_naturais, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var tab = view.findViewById<TabLayout>(R.id.layoutLogin)
        var page = view.findViewById<ViewPager>(R.id.viewPagerLogin)
        setupViewPagerAndTabLayout(tab,page)

    }

    fun setupViewPagerAndTabLayout(tab : TabLayout,page: ViewPager){

        tab.setupWithViewPager(page)
        var fragmentManager = (activity as FragmentActivity).supportFragmentManager
        page.adapter = EventoAdapter(
                listOf(EventosNaturaisAtuaisFragment(), EventosNaturaisAnterioresFragment()),
                listOf(getString(R.string.atuais), getString(R.string.anteriores)),
                fragmentManager
        )
    }

}