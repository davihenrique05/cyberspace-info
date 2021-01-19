package com.example.cyberspace_info.pesquisarimgvid.view

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.cyberspace_info.R
import com.example.cyberspace_info.listatecnologiasusadas.repository.ProjectIdRepository
import com.example.cyberspace_info.listatecnologiasusadas.viewmodel.ProjectIdViewModel
import com.example.cyberspace_info.pesquisarimgvid.model.LinksImageModel
import com.example.cyberspace_info.pesquisarimgvid.model.ObjectImageModel
import com.example.cyberspace_info.pesquisarimgvid.repository.ImageVideoRepository
import com.example.cyberspace_info.pesquisarimgvid.view.adapter.PesquisaImgVidAdapter
import com.example.cyberspace_info.pesquisarimgvid.viewmodel.ImageVideoViewModel
import com.google.android.material.tabs.TabLayout
import com.example.cyberspace_info.planetasorbitandoestrelas.view.adapter.ViewPagerAdapter
import com.google.android.material.card.MaterialCardView

class PesquisaImgVidActivity : AppCompatActivity() {

    private lateinit var _viewModel : ImageVideoViewModel
    private lateinit var _listaImagens : MutableList<ObjectImageModel>
    private lateinit var _adaptador : PesquisaImgVidAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa_img_vid)
        findViewById<ImageView>(R.id.imgViewMenuPesquisaImgVid).setOnClickListener {
            onBackPressed()
        }

        ResultarPesquisa()

        findViewById<ImageView>(R.id.imgViewProcurarPesquisaImgVid).setOnClickListener {

            var search = findViewById<TextView>(R.id.txtpesquisaimagefragment).text.toString()

            var intent = Intent(this@PesquisaImgVidActivity, PesquisaImgVidActivity::class.java)
            intent.putExtra("search",search)
            var activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                applicationContext,
                R.anim.from_right,
                R.anim.to_left
            )
            ActivityCompat.startActivity(this, intent, activityOptionsCompat.toBundle())
            //startActivity(intent)

        }

    }

    private fun ResultarPesquisa() {

        _listaImagens = mutableListOf()

        val search = intent.getStringExtra("search")

        val progresBar = findViewById<ProgressBar>(R.id.progessBar)

        showLoading(true)
        val color = ContextCompat.getColor(this,R.color.colorPrimaryDarkest)
        @Suppress("DEPRECATION")
        progresBar.indeterminateDrawable.setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY)

        _viewModel = ViewModelProvider(this, ImageVideoViewModel.ImageVideoViewModelFactory(
            ImageVideoRepository()
        )).get(ImageVideoViewModel::class.java)

        val viewManager = GridLayoutManager(this, 3)
        val recyclerView = findViewById<RecyclerView>(R.id.listaFotosVideos)
        _adaptador = PesquisaImgVidAdapter(_listaImagens){

        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = _adaptador
        }

        if (search != null) {
            _viewModel.getUrlsImages(search).observe(this) {
                if (!it.isNullOrEmpty()) {
                    exibirLista(it)
                }else{
                    showLoading(false)
                    findViewById<MaterialCardView>(R.id.cardNotFound).visibility = View.VISIBLE
                }
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = findViewById<View>(R.id.loading)

        if (isLoading) {
            viewLoading?.visibility = View.VISIBLE
        } else {
            viewLoading?.visibility = View.GONE
        }
    }


    private fun exibirLista(lista:List<ObjectImageModel>){
        _listaImagens.addAll(lista)
        showLoading(false)
        _adaptador.notifyDataSetChanged()
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