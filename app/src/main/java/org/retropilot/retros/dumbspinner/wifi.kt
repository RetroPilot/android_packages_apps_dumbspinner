package org.retropilot.retros.dumbspinner

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class wifi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)
        startActivity(Intent(Settings.ACTION_WIFI_SETTINGS));

        val settingsButton = findViewById<Button>(R.id.settings)
        settingsButton.setOnClickListener {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS));
        }
    }
}
