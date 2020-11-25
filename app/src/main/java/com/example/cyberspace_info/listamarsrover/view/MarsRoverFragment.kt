package com.example.cyberspace_info.listamarsrover.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.cyberspace_info.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_mars_rover.*
import java.util.*


class MarsRoverFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_mars_rover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagemFundo = ContextCompat.getDrawable(view.context, R.drawable.rover)
        view.findViewById<ImageView>(R.id.imgFundo_MarsRover).setImageDrawable(imagemFundo)

        val itemsRover = listOf("Curiosity", "Opportunity", "Spirit")
        val adapterRover = ArrayAdapter(requireContext(), R.layout.lista_rover, itemsRover)
        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapterRover)

        val itemsCamera = listOf("FHAZ", "RHAZ", "MAST","CHEMCAM","MAHLI","MARDI","NAVCAM","PANCAM","MINITES")
        val adapterCamera = ArrayAdapter(requireContext(), R.layout.lista_camera, itemsCamera)
        (textField1.editText as? AutoCompleteTextView)?.setAdapter(adapterCamera)

        view.findViewById<TextInputEditText>(R.id.edtMarsRover).setOnClickListener {
            abrirCalendario(view)
        }

        var imgBack = view.findViewById<ImageView>(R.id.imgReturn_fMarsRover).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.popBackStack()
        }
    }

    private fun abrirCalendario(minhaView:View) {
        DatePickerDialog(minhaView.context,
            AlertDialog.THEME_DEVICE_DEFAULT_DARK,  object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                dia = dayOfMonth
                mes = month
                ano = year
            }

        },ano, mes, dia).show()
    }
}