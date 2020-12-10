package com.example.cyberspace_info.planetasorbitandoestrelas.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.planetasorbitandoestrelas.view.adapter.PlanetasOrbitandoEstrelasAdapter
import com.example.cyberspace_info.planetasorbitandoestrelas.model.PlanetaOrbitandoEstrelaModel

class PlanetasOrbitandoEstrelasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planetas_orbitando_estrelas)

        findViewById<ImageView>(R.id.imgSetaVoltarPlanetasOrbitandoEstrelas).setOnClickListener {
            onBackPressed()
        }

        val viewManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.listaPlanetasOrbitandoEstrelas)

        var meusPlanetas = arrayListOf(
            PlanetaOrbitandoEstrelaModel(
                "Mercurio",
                "Sol",
                R.drawable.mercurio
            ),
            PlanetaOrbitandoEstrelaModel(
                "Venus",
                "Sol",
                R.drawable.venus
            ),
            PlanetaOrbitandoEstrelaModel(
                "Terra",
                "Sol",
                R.drawable.terra
            ),
            PlanetaOrbitandoEstrelaModel(
                "Marte",
                "Sol",
                R.drawable.marte
            ),
            PlanetaOrbitandoEstrelaModel(
                "Júpiter",
                "Sol",
                R.drawable.jupiter
            ),
            PlanetaOrbitandoEstrelaModel(
                "Saturno",
                "Sol",
                R.drawable.saturno
            ),
            PlanetaOrbitandoEstrelaModel(
                "Urano",
                "Sol",
                R.drawable.urano
            ),
            PlanetaOrbitandoEstrelaModel(
                "Netuno",
                "Sol",
                R.drawable.netuno
            )
        )

        val viewAdapter =
            PlanetasOrbitandoEstrelasAdapter(
                meusPlanetas
            )
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.from_left,R.anim.to_right)
    }
}