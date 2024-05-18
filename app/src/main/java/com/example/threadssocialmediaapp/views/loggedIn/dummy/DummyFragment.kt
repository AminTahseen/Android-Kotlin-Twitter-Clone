package com.example.threadssocialmediaapp.views.loggedIn.dummy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.threadssocialmediaapp.R
import com.example.threadssocialmediaapp.databinding.FragmentDummyBinding
import com.example.threadssocialmediaapp.databinding.FragmentIntroBinding
import com.example.threadssocialmediaapp.views.auth.intro.IntroEvents
import com.example.threadssocialmediaapp.views.auth.intro.IntroViewModel
import kotlinx.coroutines.launch
import java.lang.Exception


class DummyFragment : Fragment() {

    private lateinit var dummyBinding: FragmentDummyBinding
    private val dummyViewModel: DummyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dummyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dummy, container, false)
        // Inflate the layout for this fragment
        dummyBinding.viewModel = dummyViewModel
        observeEvents()
        return dummyBinding.root
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            dummyViewModel.events.collect {

            }
        }
    }
}