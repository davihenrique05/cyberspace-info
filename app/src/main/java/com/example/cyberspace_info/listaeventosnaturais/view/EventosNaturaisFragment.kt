package com.example.cyberspace_info.listaeventosnaturais.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import androidx.navigation.Navigation
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

        var navController = Navigation.findNavController(view)

        view.findViewById<ImageView>(R.id.imgReturn).setOnClickListener {
            navController.navigate(R.id.action_eventosNaturaisFragment_to_menuFragment)
        }

        var tab = view.findViewById<TabLayout>(R.id.layoutNaturalEvents)
        var page = view.findViewById<ViewPager>(R.id.viewPagerNaturalEvents)

        setupViewPagerAndTabLayout(tab,page)
    }



    fun setupViewPagerAndTabLayout(tab : TabLayout,page: ViewPager){

        tab.setupWithViewPager(page)

        var fragmentManager = (activity as AppCompatActivity).supportFragmentManager

        page.adapter = EventoAdapter(
            listOf(EventosNaturaisAtuaisFragment(), EventosNaturaisAnterioresFragment()),
            listOf(getString(R.string.atuais), getString(R.string.anteriores)),
            getChildFragmentManager()
        )


    }

}