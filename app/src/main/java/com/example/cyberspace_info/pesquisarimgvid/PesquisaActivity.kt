package com.example.cyberspace_info.pesquisarimgvid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.example.cyberspace_info.R
import kotlinx.android.synthetic.main.activity_pesquisa.*

class PesquisaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa)
        findViewById<ImageView>(R.id.imgViewMenuPesquisa).setOnClickListener {
            onBackPressed()
        }

        pesquisar()

    }

    private fun pesquisar() {
        btnpesquisar.setOnClickListener {
            var intent = Intent(this@PesquisaActivity, PesquisaImgVidActivity::class.java)
            var activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                getApplicationContext(),
                R.anim.from_right,
                R.anim.to_left
            )
            ActivityCompat.startActivity(this, intent, activityOptionsCompat.toBundle())
            //startActivity(intent)

        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.from_left,R.anim.to_right)
    }
}