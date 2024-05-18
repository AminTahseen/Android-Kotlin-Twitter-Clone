package com.example.threadssocialmediaapp.views.auth.intro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.threadssocialmediaapp.R
import com.example.threadssocialmediaapp.databinding.FragmentIntroBinding
import com.example.threadssocialmediaapp.views.loggedIn.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class IntroFragment : Fragment() {

    private lateinit var introBinding: FragmentIntroBinding
    private val introViewModel: IntroViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        introBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_intro, container, false)
        // Inflate the layout for this fragment
        introBinding.viewModel = introViewModel
        observeEvents()
        return introBinding.root
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            introViewModel.events.observe(viewLifecycleOwner) {
                when (it) {
                    IntroEvents.NavigateToCreateAccount -> {
                        try {
                            findNavController().navigate(R.id.action_introFragment_to_createAccountFragment)
                        } catch (_: Exception) {
                        }
                    }

                    IntroEvents.NavigateToDashboard -> {
                        Log.d("calledTwiice", "called....")
                        val dashboardActivity = Intent(activity, DashboardActivity::class.java)
                        startActivity(dashboardActivity)
                    }

                    else -> {}
                }
            }
        }
    }
}