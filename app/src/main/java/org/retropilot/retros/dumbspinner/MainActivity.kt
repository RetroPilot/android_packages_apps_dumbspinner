package org.retropilot.retros.dumbspinner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = intent;

        // we should really be checking these
        //val action = intent.action
        //val type = intent.type

        // Fetching intent extra text
        val desiredText = intent.getStringExtra("loading_reason")

        if (desiredText != null) {
            val loadingReason = findViewById<TextView>(R.id.loadingReason)
            loadingReason.setText(desiredText)
        }
    }
}
