package com.daydev.comicfirebase

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import android.widget.VideoView

class WatchActivity : AppCompatActivity() {

    private var videoView: VideoView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch)
        getSupportActionBar()!!.hide()
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        videoView = findViewById(R.id.VideoPlayer)
        val httpLiveUrl = intent.getStringExtra("source")
        Log.d("TVApps", "source=$httpLiveUrl")

        videoView!!.setVideoURI(Uri.parse(httpLiveUrl))
        videoView!!.setMediaController(MediaController(this))
        videoView!!.requestFocus()
        videoView!!.start()


    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            actionBar!!.hide()
        } else {
            actionBar!!.hide()
        }
    }

}