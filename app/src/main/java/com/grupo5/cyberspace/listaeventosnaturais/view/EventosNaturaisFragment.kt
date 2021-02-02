package com.grupo5.cyberspace.listaeventosnaturais.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.listaeventosnaturais.view.adapter.EventoAdapter
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

        val navController = Navigation.findNavController(view)

        view.findViewById<ImageView>(R.id.imgReturn).setOnClickListener {
            navController.navigate(R.id.action_eventosNaturaisFragment_to_menuFragment)
        }

        val tab = view.findViewById<TabLayout>(R.id.layoutNaturalEvents)
        val page = view.findViewById<ViewPager>(R.id.viewPagerNaturalEvents)

        setupViewPagerAndTabLayout(tab,page)
    }

    private fun setupViewPagerAndTabLayout(tab : TabLayout, page: ViewPager){

        tab.setupWithViewPager(page)

        (activity as AppCompatActivity).supportFragmentManager

        page.adapter = EventoAdapter(
            listOf(EventosNaturaisAtuaisFragment(), EventosNaturaisAnterioresFragment()),
            listOf(getString(R.string.atuais), getString(R.string.anteriores)),
            childFragmentManager
        )


    }

}