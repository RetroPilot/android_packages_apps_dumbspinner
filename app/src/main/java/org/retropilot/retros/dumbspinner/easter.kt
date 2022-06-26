package org.retropilot.retros.dumbspinner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import org.retropilot.retros.dumbspinner.databinding.ActivityEasterBinding


// ONLY USE THIS FROM FORK SELECTOR!!

class easter : AppCompatActivity() {

private lateinit var binding: ActivityEasterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easter)

        val videoView = findViewById<VideoView>(R.id.videoView)
        val rawId = resources.getIdentifier("war", "raw", packageName)
        val path = "android.resource://$packageName/$rawId"

        videoView.setVideoURI(Uri.parse(path))
        videoView.start();
        // loop
        videoView.setOnCompletionListener { videoView.start() }

        // exit out of video
        val main_layout = findViewById<ConstraintLayout>(R.id.main_layout)
        main_layout.setOnClickListener {
            videoView.stopPlayback()
            startActivity(Intent(this, fork_select::class.java))
        }
    }






    }