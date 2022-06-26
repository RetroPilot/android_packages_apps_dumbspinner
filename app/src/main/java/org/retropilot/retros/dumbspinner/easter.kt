package org.retropilot.retros.dumbspinner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import org.retropilot.retros.dumbspinner.databinding.ActivityEasterBinding


// ONLY USE THIS FROM FORK SELECTOR!!
// feel free to change the video, nothing too spicy pls
class easter : AppCompatActivity() {

private lateinit var binding: ActivityEasterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easter)

        // setup video uri
        val videoView = findViewById<VideoView>(R.id.videoView)
        val rawId = resources.getIdentifier("war", "raw", packageName)
        val path = "android.resource://$packageName/$rawId"

        videoView.setVideoURI(Uri.parse(path))
        videoView.start();
        // loop video
        videoView.setOnCompletionListener { videoView.start() }

        // o7
        val toast = Toast.makeText(this, "Sláva Ukrayíni!", Toast.LENGTH_SHORT)
        toast.show();

        // exit out of video
        val main_layout = findViewById<ConstraintLayout>(R.id.main_layout)
        main_layout.setOnClickListener {
            videoView.stopPlayback()
            startActivity(Intent(this, fork_select::class.java))
        }
    }
}