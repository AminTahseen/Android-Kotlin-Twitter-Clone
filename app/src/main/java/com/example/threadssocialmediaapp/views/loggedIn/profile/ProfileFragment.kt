package com.example.threadssocialmediaapp.views.loggedIn.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.threadssocialmediaapp.R
import com.example.threadssocialmediaapp.databinding.FragmentDummyBinding
import com.example.threadssocialmediaapp.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private lateinit var profileBinding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment
        profileBinding.viewModel = profileViewModel
        observeEvents()
        return profileBinding.root
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            profileViewModel.events.observe(viewLifecycleOwner) {

            }
        }
    }
}