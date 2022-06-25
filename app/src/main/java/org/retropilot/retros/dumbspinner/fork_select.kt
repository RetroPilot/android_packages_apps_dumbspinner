package org.retropilot.retros.dumbspinner

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class fork_select : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fork_select)


        val settingsButton = findViewById<ImageButton>(R.id.settingsButton)
        val fork_url = findViewById<EditText>(R.id.fork_url)
        val installButton = findViewById<Button>(R.id.installButton)

        // when settingsButton pressed launch android settings app

        // when settingsButton pressed launch android settings app
        getOverlayConsent()

        settingsButton.setOnClickListener {
            Log.d("fork_select", "Launching Settings")
            overlay()
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }


        installButton.setOnClickListener {
            Log.d("log", "settings pressed is")
        }




    }

    fun overlay() {
        val mParams: WindowManager.LayoutParams? = WindowManager.LayoutParams(
            200,
            120,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT)

        if (mParams != null) {
            mParams.gravity = Gravity.TOP or Gravity.LEFT
        };

        val testView = LayoutInflater.from(this).inflate(R.layout.overlay_return, null)

        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.addView(testView, mParams)

        testView.findViewById<Button>(R.id.button).setOnClickListener {
            // Open fork_select intent
            val intent = Intent(this, fork_select::class.java)
            startActivity(intent)
            wm.removeView(testView)
        }
    }

    fun getOverlayConsent() {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                startActivity(intent)
            }
    }
}