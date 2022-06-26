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
        val fork_select_intent = Intent(this, fork_select::class.java)
        startActivity(fork_select_intent)
        return;

        // Fetching intent extra text
        val desiredText = intent.getStringExtra("loading_reason")

        if (desiredText != null) {
            val loadingReason = findViewById<TextView>(R.id.loadingReason)
            loadingReason.setText(desiredText)
        }
    }
}
