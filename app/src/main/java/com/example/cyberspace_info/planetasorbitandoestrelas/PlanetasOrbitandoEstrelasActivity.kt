package com.example.cyberspace_info.planetasorbitandoestrelas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cyberspace_info.R

class PlanetasOrbitandoEstrelasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planetas_orbitando_estrelas)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.from_left,R.anim.to_right)
    }
}