package com.example.threadssocialmediaapp.views.loggedIn.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import com.example.threadssocialmediaapp.views.loggedIn.post.PostActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        binding.viewModel = dashboardViewModel
        setContentView(binding.root)
        binding.bottomNavigation.setupWithNavController(
            findNavController(R.id.dashboard_nav_host_fragment)
        )
        observeEvents()
        handleVisibility()
    }

    private fun handleVisibility() {
        findNavController(R.id.dashboard_nav_host_fragment).addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.postDetailsFragment) {
                binding.bottomNavigation.gone()
                binding.postFabButton.gone()
            } else if (destination.id == R.id.profileFragment) {
                binding.postFabButton.gone()
            } else {
                binding.bottomNavigation.visible()
                binding.postFabButton.visible()
            }
        }
    }

    private fun observeEvents() {
        dashboardViewModel.events.observe(this) {
            when (it) {
                DashboardEvents.NewPost -> {
                    Intent(this, PostActivity::class.java).run {
                        startActivity(this)
                    }
                }

                else -> {}
            }
        }
    }
}