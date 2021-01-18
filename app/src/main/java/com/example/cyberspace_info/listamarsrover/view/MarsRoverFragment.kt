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
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listamarsrover.repository.MarsRoverPhotosRepository
import com.example.cyberspace_info.listamarsrover.viewmodel.MarsRoverPhotosViewModel
import com.google.android.material.textfield.TextInputEditText
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

        val itemsCamera = listOf(
            "FHAZ",
            "RHAZ",
            "MAST",
            "CHEMCAM",
            "MAHLI",
            "MARDI",
            "NAVCAM",
            "PANCAM",
            "MINITES"
        )
        val adapterCamera = ArrayAdapter(requireContext(), R.layout.lista_camera, itemsCamera)
        (textField1.editText as? AutoCompleteTextView)?.setAdapter(adapterCamera)

        view.findViewById<TextInputEditText>(R.id.edtMarsRover).setOnClickListener {
            abrirCalendario(view)
        }

        view.findViewById<ImageView>(R.id.imgReturn_fMarsRover).setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_marsRoverFragment_to_menuFragment)
        }

        view.findViewById<ImageView>(R.id.imgPesquisar_fMarsRover).setOnClickListener {
            val _viewModel = ViewModelProvider(
                this,
                MarsRoverPhotosViewModel.MarsRoverPhotosViewModelFactory(MarsRoverPhotosRepository())
            ).get(MarsRoverPhotosViewModel::class.java)

            _viewModel.obterLista("curiosity","2015-6-3").observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                var lista = mutableListOf<String>()

                for (marsRover in it){
                     lista.add(marsRover.imagemURL)
                }

                val bundle = bundleOf("Origem" to getString(R.string.marsrover_comparacao), "imagens" to lista)
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_marsRoverFragment_to_galeriaFragment,bundle)
            })
        }
    }

    private fun abrirCalendario(minhaView: View) {
        DatePickerDialog(minhaView.context,
            AlertDialog.THEME_DEVICE_DEFAULT_DARK, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    dia = dayOfMonth
                    mes = month
                    ano = year
                }

            }, ano, mes, dia
        ).show()
    }
}