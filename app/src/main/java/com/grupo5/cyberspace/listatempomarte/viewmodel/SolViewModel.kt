package com.grupo5.cyberspace.listatempomarte.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.grupo5.cyberspace.listatempomarte.model.SolInfoModel
import com.grupo5.cyberspace.listatempomarte.model.SolModel
import com.grupo5.cyberspace.listatempomarte.repository.SolRepository
import com.google.gson.internal.LinkedTreeMap
import kotlinx.coroutines.Dispatchers

class SolViewModel(
    private val repository: SolRepository
) : ViewModel() {

    private var sois = mutableListOf<SolModel>()
    fun obterLista() = liveData(Dispatchers.IO) {
        val response = repository.obterSol()

        try {
            val body = response as LinkedTreeMap<String, Any>

            for (key in body.keys) {
                try {
                    if ((key.toString().toLowerCase() != "validity_checks") && (key.toString()
                            .toLowerCase() != "sol_keys")
                    ) {
                        val value = body[key] as LinkedTreeMap<String, Any>

                        var pre = LinkedTreeMap<String, Any>()
                        var at = LinkedTreeMap<String, Any>()
                        var wd = LinkedTreeMap<String, Any>()
                        var atmosphericTemperature = SolInfoModel(0.0, 0.0, 0.0, 0.0)
                        var pressure = SolInfoModel(0.0, 0.0, 0.0, 0.0)
                        var windSpeed = SolInfoModel(0.0, 0.0, 0.0, 0.0)

                        if (value.contains("AT")) {
                            at = value["AT"] as LinkedTreeMap<String, Any>
                            atmosphericTemperature = SolInfoModel(
                                at["av"] as Double,
                                at["ct"] as Double,
                                at["mn"] as Double,
                                at["mx"] as Double
                            )
                        }

                        if (value.contains("HWS")) {
                            wd = value["HWS"] as LinkedTreeMap<String, Any>
                            windSpeed = SolInfoModel(
                                wd["av"] as Double,
                                wd["ct"] as Double,
                                wd["mn"] as Double,
                                wd["mx"] as Double
                            )
                        }

                        if (value.contains("PRE")) {
                            pre = value["PRE"] as LinkedTreeMap<String, Any>
                            pressure = SolInfoModel(
                                pre["av"] as Double,
                                pre["ct"] as Double,
                                pre["mn"] as Double,
                                pre["mx"] as Double
                            )
                        }

                        val sol = SolModel(
                            key.toInt(),
                            atmosphericTemperature,
                            windSpeed,
                            pressure,
                            value["First_UTC"].toString(),
                            value["Last_UTC"].toString(),
                            value["Season"].toString()
                        )

                        sois.add(sol)
                    }
                } catch (e: Exception) {
                    e.message
                }
            }
        } catch (e: Exception) {
            println(e.stackTrace)
        }

        emit(sois)
    }

    class SolViewModelFactory(private val repository: SolRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SolViewModel(repository) as T
        }
    }
}