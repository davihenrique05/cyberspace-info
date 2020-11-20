package com.example.cyberspace_info.listaeventosnaturais.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.example.cyberspace_info.R
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

        tab.setupWithViewPager(page)

        var fragmentManager = (activity as FragmentActivity).supportFragmentManager

        page.adapter = EventosAdapter(
            listOf(EventosNaturaisAtuaisFragment(), EventosNaturaisAnterioresFragment()),
            listOf("Atuais", "Anteriores"),
            fragmentManager
        )

    }

}