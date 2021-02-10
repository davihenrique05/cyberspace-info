package com.grupo5.cyberspace.listatempomarte.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.listatempomarte.repository.SolRepository
import com.grupo5.cyberspace.listatempomarte.viewmodel.SolViewModel
import java.text.SimpleDateFormat
import java.util.*

class TempoEmMarteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tempo_em_marte, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var imgBack = view.findViewById<ImageView>(R.id.imgReturn_fTempoEmMarte).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.popBackStack()
        }

        val viewModel = ViewModelProvider(
            this,
            SolViewModel.SolViewModelFactory(SolRepository())
        ).get(SolViewModel::class.java)

        viewModel.obterLista().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            view.findViewById<TextView>(R.id.txtPressaoMax_fTempoEmMarte).text = formatarDados(it[5].pressure.mx)
            view.findViewById<TextView>(R.id.txtPressaoMin_fTempoEmMarte).text = formatarDados(it[5].pressure.mn)
            view.findViewById<TextView>(R.id.txtPressaoMed_fTempoEmMarte).text = formatarDados(it[5].pressure.av)

            view.findViewById<TextView>(R.id.txtTempMax_fTempoEmMarte).text = formatarDados(it[5].atmosphericTemperature.mx,true)
            view.findViewById<TextView>(R.id.txtTempMin_fTempoEmMarte).text = formatarDados(it[5].atmosphericTemperature.mn,true)
            view.findViewById<TextView>(R.id.txtTempMed_fTempoEmMarte).text = formatarDados(it[5].atmosphericTemperature.av,true)

            view.findViewById<TextView>(R.id.txtVentosMax_fTempoEmMarte).text = formatarDados(it[5].windSpeed.mx)
            view.findViewById<TextView>(R.id.txtVentosMin_fTempoEmMarte).text = formatarDados(it[5].windSpeed.mn)
            view.findViewById<TextView>(R.id.txtVentosMed_fTempoEmMarte).text = formatarDados(it[5].windSpeed.av)

            view.findViewById<TextView>(R.id.txtData_fTempoEmMarte).text = formatarData(it[5].firstUTC.substring(0,10),"yyyy-MM-dd","EEEE, d 'de' MMMM 'de' yyyy").capitalize()
            view.findViewById<TextView>(R.id.txtSol_fTempoEmMarte).text = "SOL ${it[5].id}"
        })
    }

    @SuppressLint("SimpleDateFormat")
    fun formatarData(data: String, formatoOrigem: String, formatoDestino: String): String {
        val locale = Locale("pt","BR")
        val formato = SimpleDateFormat(formatoOrigem,locale)
        val dataUm = formato.parse(data)
        formato.applyPattern(formatoDestino)
        return formato.format(dataUm)
    }

    private fun formatarDados(valor: Double, isTemperatura: Boolean = false):String{
        return if (valor != 0.0){
            if (isTemperatura) {
                "%.2f".format(valor) + " °C"
            } else {
                "%.2f".format(valor)
            }
        } else {
            "S/D"
        }
    }
}