package com.grupo5.cyberspace.main.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.autenticacao.view.AutenticacaoActivity
import com.grupo5.cyberspace.menu.view.MainActivity
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.animacao)
        lottieAnimationView.animate()
        val bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        findViewById<TextView>(R.id.txtSplash).animation = bottomAnim

        val mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if (currentUser != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, AutenticacaoActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 7501)

    }
}