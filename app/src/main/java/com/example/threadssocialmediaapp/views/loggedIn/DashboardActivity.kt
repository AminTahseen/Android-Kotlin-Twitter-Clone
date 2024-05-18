package com.example.threadssocialmediaapp.views.loggedIn

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.threadssocialmediaapp.R
import com.example.threadssocialmediaapp.databinding.ActivityDashboardBinding
import com.example.threadssocialmediaapp.utils.gone
import com.example.threadssocialmediaapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.setupWithNavController(
            findNavController(R.id.dashboard_nav_host_fragment)
        )

        findNavController(R.id.dashboard_nav_host_fragment).addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.postDetailsFragment) {
                binding.bottomNavigation.gone()
                binding.postFabButton.gone()
            } else {
                binding.bottomNavigation.visible()
                binding.postFabButton.visible()
            }
        }
    }
}