package com.example.cyberspace_info.asteroidesemcolisao.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.cyberspace_info.asteroidesemcolisao.model.AsteroideModel
import com.example.cyberspace_info.asteroidesemcolisao.repository.AsteroidesRepository
import com.example.cyberspace_info.asteroidesemcolisao.view.BottomSheetAsteroideFragment
import com.google.gson.internal.LinkedTreeMap
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AsteroidesEmColisaoViewModel(
    private val repository: AsteroidesRepository
):ViewModel() {

    private var _lista = mutableListOf<AsteroideModel>()

    fun obterLista() = liveData(Dispatchers.IO) {
        val dataAtual = obterDiaDeHoje()
        val dataFinal = intervaloDia()
        val response = repository.obterListaDeAsteroides(dataAtual, dataFinal)

        try{
            val body = response.asteiroides as LinkedTreeMap<String, Any>

            for(i in body.keys){
                val atual = body[i] as List<LinkedTreeMap<String, Any>>

                for(it in atual){

                    val asteroid = mapearTreeMap(it)
                    _lista.add(asteroid)
                }
            }
            emit(_lista)
        }catch (e: Exception){
            println(e.stackTrace)
        }
    }


    fun obterDiaDeHoje():String {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val string = "${year}-${String.format("%02d", month)}-${String.format("%02d", day)}"

        return string
    }

    fun intervaloDia():String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val data = obterDiaDeHoje()
        val parsed = dateFormat.parse(data)
        val c = Calendar.getInstance()

        c.time = parsed
        c.add(Calendar.DAY_OF_YEAR, -1)

        val dataNova = c.time
        val string = dateFormat.format(dataNova)

        return string
    }

    fun showBottomSheet(context: Context, it: AsteroideModel){
        val bottomSheetFragment =  BottomSheetAsteroideFragment()
        val bundle = bundleOf("Nome" to it.nome,
                                        "min" to it.diametroMinimo,
                                        "max" to it.diametroMaximo,
                                        "velocidade" to it.velocidadeModelRel,
                                        "link" to it.link)

        bottomSheetFragment.arguments = bundle
        bottomSheetFragment.show((context as AppCompatActivity).supportFragmentManager,"BottomSheetDialog")
    }

    fun mapearTreeMap(it: LinkedTreeMap<String, Any>): AsteroideModel{

        val diametros = it["estimated_diameter"] as LinkedTreeMap<String,Any>
        val metros = diametros["meters"] as LinkedTreeMap<String, Double>
        val data = it["close_approach_data"] as List<LinkedTreeMap<String,Any>>
        val velocidade = data[0]["relative_velocity"] as LinkedTreeMap<String, String>
        val asteroid = AsteroideModel(
            it["name"].toString(),
            it["nasa_jpl_url"].toString(),
            metros["estimated_diameter_min"]!!.toDouble(),
            metros["estimated_diameter_max"]!!.toDouble(),
            data[0]["close_approach_date"].toString(),
            velocidade["kilometers_per_hour"]!!.toDouble()
        )
        return asteroid
    }
    class AsteroidesEmColisaoViewModelFactory(private val repository: AsteroidesRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AsteroidesEmColisaoViewModel(repository) as T
        }
    }
}