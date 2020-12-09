package com.example.cyberspace_info.listaeventosnaturais.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class EventoAdapter(private val frags:List<Fragment>,private val title:List<String>, manager:FragmentManager) : FragmentPagerAdapter(manager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment = frags[position]
    override fun getCount(): Int = frags.size
    override fun getPageTitle(position: Int): CharSequence? = title[position]


}