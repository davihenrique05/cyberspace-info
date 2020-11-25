package com.example.cyberspace_info.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.example.cyberspace_info.R
import com.example.cyberspace_info.autenticacao.view.AutenticacaoActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.animacao)
        lottieAnimationView.animate()

        val bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        findViewById<TextView>(R.id.txtSplash).animation = bottomAnim

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AutenticacaoActivity::class.java)
            startActivity(intent)
        },7500)
    }
}