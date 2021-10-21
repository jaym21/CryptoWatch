package dev.jaym21.cryptowatch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navView: BottomNavigationView = binding!!.navBottom

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}