package com.example.cyberspace_info.listatempomarte.view

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
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listatempomarte.repository.SolRepository
import com.example.cyberspace_info.listatempomarte.viewmodel.SolViewModel
import java.util.*

class TempoEmMarteFragment : Fragment() {
    private var dia: Int
    private var mes: Int
    private var ano: Int

    init {
        val calendar = Calendar.getInstance()
        dia = calendar.get(Calendar.DAY_OF_MONTH)
        mes = calendar.get(Calendar.MONTH)
        ano = calendar.get(Calendar.YEAR)
    }


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

            view.findViewById<TextView>(R.id.txtData_fTempoEmMarte).text = it[0].firstUTC
            view.findViewById<TextView>(R.id.txtSol_fTempoEmMarte).text = "SOL " + it[0].id.toString()
        })
    }

    private fun formatarDados(valor: Double, isTemperatura: Boolean = false):String{
        return if (valor != 0.0){
            if (isTemperatura) {
                "%.2f".format(valor) + " °C"
            } else {
                "%.2f".format(valor)
            }
        } else {
            "-"
        }
    }
}