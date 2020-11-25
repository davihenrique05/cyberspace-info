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
import androidx.navigation.Navigation
import com.example.cyberspace_info.R
import java.text.SimpleDateFormat
import java.util.*

class TempoEmMarteFragment : Fragment() {
    var dia: Int
    var mes: Int
    var ano: Int

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.imgCalendario).setOnClickListener {
            abrirCalendario(view)
        }
        var imgBack = view.findViewById<ImageView>(R.id.imgReturn_fTempoEmMarte).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.popBackStack()
        }
    }

    private fun abrirCalendario(minhaView:View) {
        DatePickerDialog(minhaView.context,AlertDialog.THEME_DEVICE_DEFAULT_DARK,  object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                dia = dayOfMonth
                mes = month
                ano = year
            }

        },ano, mes, dia).show()
    }


}