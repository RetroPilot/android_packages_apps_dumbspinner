package org.retropilot.retros.dumbspinner

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // open fork_select intent
        //startActivity(Intent(this, wifi::class.java))
        //return;

        // Fetching intent extra text
        val desiredText = intent.getStringExtra("loading_reason")
        val desiredText2 = intent.getStringExtra("loading_reason2")

        if (desiredText != null) {
            val loadingReason = findViewById<TextView>(R.id.loadingReason)
            loadingReason.setText(desiredText)
        }
        if (desiredText2 != null) {
            val loadingReason2 = findViewById<TextView>(R.id.loadingReason2)
            loadingReason2.setText(desiredText2)
        }

        val ipText = findViewById<TextView>(R.id.connection)
        ipText.setText("LAN IP: ${getIpv4HostAddress()}")
    }

    fun getIpv4HostAddress(): String {
        NetworkInterface.getNetworkInterfaces()?.toList()?.map { networkInterface ->
            networkInterface.inetAddresses?.toList()?.find {
                !it.isLoopbackAddress && it is Inet4Address
            }?.let { return it.hostAddress }
        }
        return "No Network"
    }
}
