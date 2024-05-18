package com.example.threadssocialmediaapp.views.loggedIn.likes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.threadssocialmediaapp.R
import com.example.threadssocialmediaapp.databinding.FragmentDummyBinding
import com.example.threadssocialmediaapp.databinding.FragmentLikesBinding
import com.example.threadssocialmediaapp.views.loggedIn.dummy.DummyViewModel
import kotlinx.coroutines.launch


class LikesFragment : Fragment() {
    private lateinit var likesBinding: FragmentLikesBinding
    private val likesViewModel: LikesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        likesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_likes, container, false)
        // Inflate the layout for this fragment
        likesBinding.viewModel = likesViewModel
        observeEvents()
        return likesBinding.root
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            likesViewModel.events.observe(viewLifecycleOwner) {

            }
        }
    }
}