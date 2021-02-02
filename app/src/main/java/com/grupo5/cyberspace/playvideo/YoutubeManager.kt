package com.grupo5.cyberspace.playvideo

import android.widget.ImageView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import com.grupo5.cyberspace.R
import java.util.regex.Matcher
import java.util.regex.Pattern

object YoutubeManager {

    fun extrairIDUrl(urlVideo: String): String? {
        val pattern =
            "https?://(?:[0-9A-Z-]+\\.)?(?:youtu\\.be/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|</a>))[?=&+%\\w]*"

        val compiledPattern: Pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher =
            compiledPattern.matcher(urlVideo) //url is youtube url for which you want to extract the id.

        if (matcher.find()) {
            return matcher.group(1)
        }
        return null
    }

    fun getIdVideo(urlVideo: String): String? {
        val pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
        val compiledPattern: Pattern = Pattern.compile(pattern)
        val matcher: Matcher = compiledPattern.matcher(urlVideo)
        return if (matcher.find()) {
            matcher.group()
        } else {
            null
        }
    }

    fun setThumbnail(thumbnail: YouTubeThumbnailView, urlVideo: String, apiKey :String){
        thumbnail.initialize(apiKey,object :YouTubeThumbnailView.OnInitializedListener{
            override fun onInitializationSuccess(
                thumbView: YouTubeThumbnailView?,
                thumbLoader: YouTubeThumbnailLoader?
            ) {

                var id = extrairIDUrl(urlVideo)
                if(id== null){
                    id = getIdVideo(urlVideo)
                }
                thumbLoader!!.setVideo(id)
                thumbView!!.scaleType = ImageView.ScaleType.FIT_XY

                thumbLoader.setOnThumbnailLoadedListener(object : YouTubeThumbnailLoader.OnThumbnailLoadedListener{

                    override fun onThumbnailLoaded(p0: YouTubeThumbnailView?, p1: String?) {
                        thumbLoader.release()
                    }

                    override fun onThumbnailError(
                        p0: YouTubeThumbnailView?,
                        p1: YouTubeThumbnailLoader.ErrorReason?
                    ) {
                        thumbLoader.release()
                    }

                })
            }

            override fun onInitializationFailure(
                p0: YouTubeThumbnailView?,
                p1: YouTubeInitializationResult?
            ) {
                p0!!.setImageResource(R.drawable.space_cats)
            }

        })
    }
}