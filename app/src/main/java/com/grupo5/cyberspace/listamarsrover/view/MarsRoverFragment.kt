package com.grupo5.cyberspace.listamarsrover.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.icu.text.CaseMap
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.listamarsrover.repository.MarsRoverPhotosRepository
import com.grupo5.cyberspace.listamarsrover.viewmodel.MarsRoverPhotosViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_mars_rover.*
import java.text.SimpleDateFormat
import java.util.*

class MarsRoverFragment : Fragment() {
    private var dia: Int
    private var mes: Int
    private var ano: Int
    private var indexRover: Int

    private lateinit var edtRover: AutoCompleteTextView
    private lateinit var edtData: TextInputEditText
    private lateinit var btnPesquisar: Button
    private lateinit var txtRover: TextInputLayout
    private lateinit var txtData: TextInputLayout

    init {
        val calendar = Calendar.getInstance()
        dia = calendar.get(Calendar.DAY_OF_MONTH)
        mes = calendar.get(Calendar.MONTH)
        ano = calendar.get(Calendar.YEAR)

        indexRover = -1
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

        edtRover = view.findViewById<AutoCompleteTextView>(R.id.edtRover_fMarsRover)
        edtData = view.findViewById<TextInputEditText>(R.id.edtData_fMarsRover)
        btnPesquisar = view.findViewById<Button>(R.id.btnPesquisar_fMarsRover)
        txtRover = view.findViewById<TextInputLayout>(R.id.txtRover_fMarsRover)
        txtData = view.findViewById<TextInputLayout>(R.id.txtData_fMarsRover)

        edtData.setText(formatarData("$dia/$mes/$ano","dd/mm/yyyy","DD/MM/yyyy"))
        edtData.isEnabled = false

        edtRover.setKeyListener(null);

        val itemsRover = listOf("Curiosity", "Opportunity", "Spirit")
        val adapterRover = ArrayAdapter(requireContext(), R.layout.list_item_rover, itemsRover)
        (txtRover.editText as? AutoCompleteTextView)?.setAdapter(adapterRover)

        edtRover.setOnItemClickListener { parent, view, position, id ->
            indexRover = position
        }

        txtData.setEndIconOnClickListener {
            abrirCalendario(view)
        }

        view.findViewById<ImageView>(R.id.imgReturn_fMarsRover).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.popBackStack()
        }

        view.findViewById<Button>(R.id.btnPesquisar_fMarsRover).setOnClickListener {
            if (consisteTela(edtData, edtRover)) {
                val viewModel = ViewModelProvider(
                    this,
                    MarsRoverPhotosViewModel.MarsRoverPhotosViewModelFactory(
                        MarsRoverPhotosRepository()
                    )
                ).get(MarsRoverPhotosViewModel::class.java)

                val dataMarsRover = formatarData(edtData.text.toString(),"DD/MM/yyyy", "yyyy-MM-DD")

                viewModel.obterLista(itemsRover[indexRover].toLowerCase(), dataMarsRover)
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
            }
        }
    }

    private fun abrirCalendario(minhaView: View) {
        val dialog = DatePickerDialog(
            minhaView.context,
            android.R.style.Theme_Material_Light_Dialog_Alert,
            { view, year, month, dayOfMonth ->
                val mesRetorno = month + 1

                edtData.setText(formatarData("$dayOfMonth-$mesRetorno-$year","dd-mm-yyyy","DD/MM/yyyy"))
            }, ano, mes, dia
        )
        dialog.datePicker.maxDate = Date().time
        dialog.show()
    }

    private fun consisteTela(
        edtDataMarsRover: TextInputEditText,
        edtRover: AutoCompleteTextView
    ): Boolean {
        if (edtDataMarsRover.text.toString() == "") {
            edtDataMarsRover.error = "Informe a data da Terra"
            edtDataMarsRover.requestFocus()
            return false
        } else {
            if (indexRover == -1) {
                edtRover.error = "Informe o rover"
                edtRover.requestFocus()
                return false
            }
        }

        return true
    }

    @SuppressLint("SimpleDateFormat")
    fun formatarData(data: String, formatoOrigem:String, formatoDestino:String): String {
        val formato = SimpleDateFormat(formatoOrigem)
        val dataUm = formato.parse(data)
        formato.applyPattern(formatoDestino)
        return formato.format(dataUm)
    }
}