package com.example.cyberspace_info.pesquisarimgvid

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.example.cyberspace_info.R
import com.google.android.material.tabs.TabLayout
import com.example.cyberspace_info.planetasorbitandoestrelas.ViewPagerAdapter

class PesquisaImgVidActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa_img_vid)
        findViewById<ImageView>(R.id.imgViewMenuPesquisaImgVid).setOnClickListener {
            onBackPressed()
        }

        ResultarPesquisa()

    }

    private fun ResultarPesquisa() {
        val pager = findViewById<ViewPager>(R.id.viewPager)
        val tab = findViewById<TabLayout>(R.id.tabLayout)

        //Faz com que o tab use o Viewpager
        tab.setupWithViewPager(pager)

        val fragments = listOf(
            ResultadosPesquisaFragment.newInstance("Minhas Imagens pesquisadas", true),
            ResultadosPesquisaFragment.newInstance("Meus Vídeos pesquisados", false)
        )

        val titulos = listOf(
            getString(R.string.imagens), getString(R.string.videos)
        )

        pager.adapter =
            ViewPagerAdapter(
                fragments,
                titulos,
                supportFragmentManager
            )
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.from_left,R.anim.to_right)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}