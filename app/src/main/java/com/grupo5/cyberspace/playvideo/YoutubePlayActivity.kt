package com.grupo5.cyberspace.playvideo

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.grupo5.cyberspace.R
import com.grupo5.cyberspace.menu.view.MainActivity

class YoutubePlayActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private lateinit var _id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_play)

        val url = intent.getStringExtra("url")

        if(url != null){
            _id = YoutubeManager.extrairIDUrl(url).toString()

            if(_id.isBlank()){
                _id = YoutubeManager.getIdVideo(url).toString()
            }
        }


        findViewById<YouTubePlayerView>(R.id.youtubePlayer).initialize(
            getString(R.string.yotubeAPI),
            this
        )

        findViewById<ImageView>(R.id.closeYtb).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {

        if (!p2 && _id.isNotBlank()) {
            p1!!.cueVideo(_id)
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        Toast.makeText(this, "Ocorreu um erro", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}