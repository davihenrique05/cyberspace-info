package com.example.cyberspace_info.listamarsrover.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listamarsrover.repository.MarsRoverPhotosRepository
import com.example.cyberspace_info.listamarsrover.viewmodel.MarsRoverPhotosViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*


class MarsRoverFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_mars_rover, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner = view.findViewById<Spinner>(R.id.rover_spinner)
        val adapter = ArrayAdapter.createFromResource(
            view.context,
            R.array.rover_array, R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        view.findViewById<TextInputLayout>(R.id.textField).setEndIconOnClickListener {
            abrirCalendario(view)
        }

        view.findViewById<ImageView>(R.id.imgReturn_fMarsRover).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_marsRoverFragment_to_menuFragment)
        }

        view.findViewById<ImageView>(R.id.imgPesquisar_fMarsRover).setOnClickListener {
            if (consisteTela(view.findViewById(R.id.textd),view.findViewById(R.id.rover_spinner))) {
                val viewModel = ViewModelProvider(
                    this,
                    MarsRoverPhotosViewModel.MarsRoverPhotosViewModelFactory(
                        MarsRoverPhotosRepository()
                    )
                ).get(MarsRoverPhotosViewModel::class.java)

                val dataMarsRover = view.findViewById<TextInputEditText>(R.id.textd).text.toString()
                val dia = dataMarsRover.substring(0, 2)
                val mes = dataMarsRover.substring(3, 5)
                val ano = dataMarsRover.substring(6, 10)

                val spnRover = view.findViewById<Spinner>(R.id.rover_spinner)
                viewModel.obterLista(spnRover.selectedItem.toString().toLowerCase(), "$ano-$mes-$dia")
                    .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                        val lista = mutableListOf<String>()

                        for (marsRover in it) {
                            lista.add(marsRover.imagemURL)
                        }

                        val bundle = bundleOf(
                            "Origem" to getString(R.string.marsrover_comparacao),
                            "imagens" to lista
                        )
                        val navController = Navigation.findNavController(view)
                        navController.navigate(
                            R.id.action_marsRoverFragment_to_galeriaFragment,
                            bundle
                        )
                    })
            } else {
                view.findViewById<TextInputEditText>(R.id.textd).error = "Informe a data"
            }
        }
    }

    private fun abrirCalendario(minhaView: View) {
        val dialog = DatePickerDialog(
            minhaView.context,
            AlertDialog.THEME_DEVICE_DEFAULT_DARK,
            { view, year, month, dayOfMonth ->
                val mesRetorno = month + 1

                var diaString = ""
                if (dayOfMonth < 10) {
                    diaString = "0"
                }

                var mesString = ""
                if (mesRetorno < 10) {
                    mesString = "0"
                }
                mesString += mesRetorno
                diaString += dayOfMonth

                minhaView.findViewById<TextInputEditText>(R.id.textd)
                    .setText("$diaString/$mesString/$year")
            }, ano, mes, dia
        )
        dialog.datePicker.maxDate = Date().time
        dialog.show()
    }

    private fun consisteTela(edtDataMarsRover:TextInputEditText, spnRover:Spinner):Boolean{
        if (edtDataMarsRover.text.toString() == "") {
            edtDataMarsRover.error = "Informe a data da Terra"
            return false
        } else {
        }

        return true
    }
}