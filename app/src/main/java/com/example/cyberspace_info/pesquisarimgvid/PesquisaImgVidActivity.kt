package com.example.cyberspace_info.pesquisarimgvid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.example.cyberspace_info.R
import com.google.android.material.tabs.TabLayout
import java.lang.reflect.Array.newInstance
import android.widget.TableLayout

class PesquisaImgVidActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa_img_vid)

        findViewById<ImageView>(R.id.imgViewMenuPesquisaImgVid).setOnClickListener {
            onBackPressed()
        }

        val pager = findViewById<ViewPager>(R.id.viewPager)
        val tab = findViewById<TabLayout>(R.id.tabLayout)

        //Faz com que o tab use o Viewpager
        tab.setupWithViewPager(pager)

        val fragments = listOf(
            ResultadosPesquisaFragment.newInstance("Minhas Imagens pesquisadas", true),
            ResultadosPesquisaFragment.newInstance("Meus Vídeos pesquisados",false)
        )

        val titulos = listOf(
            getString(R.string.imagens), getString(R.string.videos)
        )

        pager.adapter = ViewPagerAdapter(fragments, titulos, supportFragmentManager)


    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.from_left,R.anim.to_right)
    }
}