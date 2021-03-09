package com.grupo5.cyberspace.asteroidesemcolisao.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.grupo5.cyberspace.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat

class BottomSheetAsteroideFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_asteroide, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nome = arguments?.getString("Nome")
        val min = arguments?.getDouble("min")
        val max = arguments?.getDouble("max")
        val velocidade = arguments?.getDouble("velocidade")
        val link = arguments?.getString("link")
        val data = arguments?.getString("data")
        val distancia = arguments?.getDouble("distancia")
        val minFor = String.format("%.2f", min)
        val maxFor = String.format("%.2f", max)
        val velocidadeFor = String.format("%.2f", velocidade)
        val distanciaFor = String.format("%.2f",distancia)


        view.findViewById<TextView>(R.id.txtTitleBottomAsteroid).text = nome
        view.findViewById<TextView>(R.id.txtDiametroMaxBottomAsteroide).text = "$maxFor km"
        view.findViewById<TextView>(R.id.txtDiametroMinBottomAsteroide).text = "$minFor km"
        view.findViewById<TextView>(R.id.txtVelocidadeBottom).text = "$velocidadeFor km/h"
        view.findViewById<TextView>(R.id.txtDataAproximacao).text = formatarData(data!!)
        view.findViewById<TextView>(R.id.txtProximidadeDaTerra).text = "$distanciaFor km"

        view.findViewById<MaterialButton>(R.id.btnMoreInfo).setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(i)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun formatarData(data:String): String {
        val formato = SimpleDateFormat("yyyy-MM-dd")
        val dataUm = formato.parse(data)
        formato.applyPattern("dd-MM-yyyy")
        return formato.format(dataUm)
    }
}