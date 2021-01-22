package com.example.cyberspace_info.listatempomarte.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listamarsrover.repository.MarsRoverPhotosRepository
import com.example.cyberspace_info.listamarsrover.viewmodel.MarsRoverPhotosViewModel
import com.example.cyberspace_info.listatempomarte.repository.SolRepository
import com.example.cyberspace_info.listatempomarte.viewmodel.SolViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var imgBack = view.findViewById<ImageView>(R.id.imgReturn_fTempoEmMarte).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.popBackStack()
        }

        val _viewModel = ViewModelProvider(
            this,
            SolViewModel.SolViewModelFactory(SolRepository())
        ).get(SolViewModel::class.java)

        _viewModel.obterLista().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            view.findViewById<TextView>(R.id.txtPressaoMax_fTempoEmMarte).text = formatarDados(it[0].pressure.mx)
            view.findViewById<TextView>(R.id.txtPressaoMin_fTempoEmMarte).text = formatarDados(it[0].pressure.mn)
            view.findViewById<TextView>(R.id.txtPressaoMed_fTempoEmMarte).text = formatarDados(it[0].pressure.av)

            view.findViewById<TextView>(R.id.txtTempMax_fTempoEmMarte).text = formatarDados(it[0].atmosphericTemperature.mx,true)
            view.findViewById<TextView>(R.id.txtTempMin_fTempoEmMarte).text = formatarDados(it[0].atmosphericTemperature.mn,true)
            view.findViewById<TextView>(R.id.txtTempMed_fTempoEmMarte).text = formatarDados(it[0].atmosphericTemperature.av,true)

            view.findViewById<TextView>(R.id.txtVentosMax_fTempoEmMarte).text = formatarDados(it[0].windSpeed.mx)
            view.findViewById<TextView>(R.id.txtVentosMin_fTempoEmMarte).text = formatarDados(it[0].windSpeed.mn)
            view.findViewById<TextView>(R.id.txtVentosMed_fTempoEmMarte).text = formatarDados(it[0].windSpeed.av)

            view.findViewById<TextView>(R.id.txtData_fTempoEmMarte).text = formatarData(it[0].firstUTC)
            view.findViewById<TextView>(R.id.txtSol_fTempoEmMarte).text = "SOL " + it[0].id.toString()
        })
    }

    fun formatarData(valor: String):String{
        return valor.substring(8,10)+" de "+meses(valor.substring(5,7))+" de "+valor.substring(0,4)
    }

    fun meses(mes:String):String{
        var descMes = ""
        when (mes) {
            "01" -> descMes = "janeiro"
            "02" -> descMes = "fevereiro"
            "03" -> descMes = "março"
            "04" -> descMes = "abril"
            "05" -> descMes = "maio"
            "06" -> descMes = "junho"
            "07" -> descMes = "julho"
            "08" -> descMes = "agosto"
            "09" -> descMes = "setembro"
            "10" -> descMes = "outubro"
            "11" -> descMes = "novembro"
            "12" -> descMes = "dezembro"
        }
        return descMes
    }

    fun formatarDados(valor: Double, isTemperatura: Boolean = false):String{
        if (valor != 0.0){
            if (isTemperatura) {
                return "%.2f".format(valor) + " °C"
            } else {
                return "%.2f".format(valor)
            }
        } else {
            return "S/D"
        }
    }
}