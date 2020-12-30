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
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.example.cyberspace_info.R

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
        findViewById<ImageView>(R.id.imgBtnPesquisar).setOnClickListener {

            val search = findViewById<TextView>(R.id.txtPesquisaImage).text.toString()

            val intent = Intent(this@PesquisaActivity, PesquisaImgVidActivity::class.java)
            intent.putExtra("search",search)
            val activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                applicationContext,
                R.anim.from_right,
                R.anim.to_left
            )
            ActivityCompat.startActivity(this, intent, activityOptionsCompat.toBundle())

        }
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