package org.retropilot.retros.dumbspinner

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.webkit.URLUtil
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


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
        }

        settingsButton.setOnClickListener {
            Log.d("fork_select", "Launching Settings")
            launchSettingsWithOverlay()
        }

        installButton.setOnClickListener {
            Log.d("log", "settings pressed is")

            installFork("ee")
        }


    }


    fun installFork(remote: String?) {

        val executor: ExecutorService = Executors.newFixedThreadPool(1)


        /*  Couldn't get this to work, id like to pass the env to .exec but no cigar
        val env = arrayOf(
        "HOME=/data/data/com.termux/files/home",
        "PATH=/data/data/com.termux/files/usr/bin:/bin",
        "LD_LIBRARY_PATH=/data/data/com.termux/files/usr/lib:/data/data/com.termux/files/usr/local/lib64"
        )*/

        val fork_url = findViewById<EditText>(R.id.fork_url)

        if (URLUtil.isValidUrl(fork_url.text.toString())) {
            executor.execute(Runnable {
                try {
                    // su didn't pipe output, would rather manage the entire
                    // fetching progress in this process, currently jerry rigged
                    // in shell scripts
                    val process = Runtime.getRuntime().exec(
                        arrayOf(
                            "su",
                            "-c",
                            "/system/bin/retros_git.sh ${fork_url.text.toString()}"
                        )/*, env*/
                    )
                    Toast.makeText(this, "fetching", Toast.LENGTH_LONG).show()
                    // retros_git.sh will fire an intent to reopen spinner
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            })
        } else {
            val toast = Toast.makeText(this, "invalid url", Toast.LENGTH_SHORT)
            toast.show();
        }

    }

    // I guess this could be split off into its own activity

    var handler: Handler = Handler()
    var checkOverlaySetting: Runnable = object : Runnable {
        override fun run() {
            if (Settings.canDrawOverlays(applicationContext)) {
                //You have the permission, re-launch MainActivity
                startActivity(Intent(applicationContext, fork_select::class.java))

                // really we don't need to reopen the fork selector activity
                // however, quick and dirty, so, it is what it is.
                launchSettingsWithOverlay()

                return;
            }
            handler.postDelayed(this, 250)
        }
    }

    fun launchSettingsWithOverlay() {
        if (!Settings.canDrawOverlays(this)) {
            val intent =
                Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
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
                // seems maybe better to just back press instead?
                this.onBackPressed()
                // Open fork_select intent
                //val intent = Intent(this, fork_select::class.java)
                //startActivity(intent)
                wm.removeView(floatingView)
            }
        }
    }

}