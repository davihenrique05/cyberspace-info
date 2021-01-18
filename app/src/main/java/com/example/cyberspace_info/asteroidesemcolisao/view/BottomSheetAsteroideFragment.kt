package com.example.cyberspace_info.asteroidesemcolisao.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.cyberspace_info.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class BottomSheetAsteroideFragment : BottomSheetDialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_asteroide,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nome = arguments?.getString("Nome")
        val min = arguments?.getDouble("min")
        val max = arguments?.getDouble("max")
        val velocidade = arguments?.getDouble("velocidade")
        val link = arguments?.getString("link")

        view.findViewById<TextView>(R.id.txtTitleBottomAsteroid).text = nome
        view.findViewById<TextView>(R.id.txtDiametroMaxBottomAsteroide).text = String.format("%.2f",max)
        view.findViewById<TextView>(R.id.txtDiametroMinBottomAsteroide).text = String.format("%.2f",min)
        view.findViewById<TextView>(R.id.txtVelocidadeBottom).text = String.format("%.2f",velocidade)

        view.findViewById<MaterialButton>(R.id.btnMoreInfo).setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(i)
        }
    }

}