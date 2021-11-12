package dev.jaym21.cryptowatch.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isConnectionAvailable()){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("networkAvailable", false)
            startActivity(intent)
            finish()
        }
    }

    private fun isConnectionAvailable(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //getting network object of currently active data network
        val network = connectivityManager.activeNetwork ?: return false
        //getting capabilities of active network representation
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            //wifi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            //has cellular network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            else -> false
        }
    }
}