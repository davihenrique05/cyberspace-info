package com.example.cyberspace_info.listamarsrover.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.cyberspace_info.R
import kotlinx.android.synthetic.main.fragment_mars_rover.*


class MarsRoverFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mars_rover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagemFundo = ContextCompat.getDrawable(view.context, R.drawable.rover)
        view.findViewById<ImageView>(R.id.imgFundo_MarsRover).setImageDrawable(imagemFundo)

        val items = listOf("Material", "Design", "Components", "Android")
        val adapter = ArrayAdapter(requireContext(), R.layout.lista_rover, items)
        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
    companion object {

    }
}