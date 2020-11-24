package com.example.cyberspace_info.planetasorbitandoestrelas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.navigation.Navigation
import com.example.cyberspace_info.R
import com.example.cyberspace_info.pesquisarimgvid.PesquisaImgVidActivity
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_pesquisa.*

class PesquisaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa)
        findViewById<ImageView>(R.id.imgViewMenuPesquisa).setOnClickListener {
            onBackPressed()
        }

        btnpesquisar.setOnClickListener {
            var intent = Intent(this@PesquisaActivity, PesquisaImgVidActivity::class.java)
            startActivity(intent)

        }


    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.from_left,R.anim.to_right)
    }
}