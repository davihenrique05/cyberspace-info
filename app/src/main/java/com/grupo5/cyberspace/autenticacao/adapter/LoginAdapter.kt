package com.grupo5.cyberspace.autenticacao.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class LoginAdapter(
    private val frags: List<Fragment>,
    private val nomes: List<String>,
    manager: FragmentManager
) :FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{
    override fun getCount() = frags.size

    override fun getItem(position: Int) = frags[position]

    override fun getPageTitle(position: Int) = nomes[position]

}