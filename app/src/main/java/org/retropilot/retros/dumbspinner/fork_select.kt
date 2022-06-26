package org.retropilot.retros.dumbspinner

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class fork_select : AppCompatActivity() {

    var easterEgg = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fork_select)


        val settingsButton = findViewById<ImageButton>(R.id.settingsButton)
        val fork_url = findViewById<EditText>(R.id.fork_url)
        val retroIcon = findViewById<ImageButton>(R.id.retroBrand)
        val installButton = findViewById<Button>(R.id.installButton)


        // we should already have this if user is using RetrOS
        // may as well check for non Retros devices.

        retroIcon.setOnClickListener {
            easterEgg++;

            if (easterEgg > 5) {
                startActivity(Intent(this, easter::class.java))
            }
            // incriment easterEgg

        }

        settingsButton.setOnClickListener {
            Log.d("fork_select", "Launching Settings")
            launchSettingsWithOverlay()
        }

        installButton.setOnClickListener {
            Log.d("log", "settings pressed is")
        }


    }


    // I guess this could be split off into its own activity

    var handler: Handler = Handler()
    var checkOverlaySetting: Runnable = object : Runnable {
        override fun run() {
            if (Settings.canDrawOverlays(applicationContext)) {
                //You have the permission, re-launch MainActivity
                val fork_select_intent = Intent(applicationContext, fork_select::class.java)
                startActivity(fork_select_intent)
                return
            }
            handler.postDelayed(this, 250)
        }
    }

    fun launchSettingsWithOverlay() {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivity(intent)
            handler.postDelayed(checkOverlaySetting, 250);
        } else {
            val mParams: WindowManager.LayoutParams? = WindowManager.LayoutParams(
                200,
                120,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )

            if (mParams != null) {
                mParams.gravity = Gravity.TOP or Gravity.RIGHT
            };

            val floatingView = LayoutInflater.from(this).inflate(R.layout.overlay_return, null)

            val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            wm.addView(floatingView, mParams)

            startActivity(Intent(Settings.ACTION_SETTINGS))

            floatingView.findViewById<Button>(R.id.button).setOnClickListener {
                // Open fork_select intent
                val intent = Intent(this, fork_select::class.java)
                startActivity(intent)
                wm.removeView(floatingView)
            }
        }
    }

}