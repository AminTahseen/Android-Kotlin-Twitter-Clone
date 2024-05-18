package com.example.threadssocialmediaapp.views.auth.createAccount

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
import com.example.threadssocialmediaapp.databinding.FragmentCreateAccountBinding
import com.example.threadssocialmediaapp.databinding.FragmentIntroBinding
import com.example.threadssocialmediaapp.views.auth.intro.IntroEvents
import com.example.threadssocialmediaapp.views.auth.intro.IntroViewModel
import kotlinx.coroutines.launch
import java.lang.Exception


class CreateAccountFragment : Fragment() {
    private lateinit var createAccountBinding: FragmentCreateAccountBinding
    private val createAccountViewModel: CreateAccountViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        createAccountBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_account, container, false)
        // Inflate the layout for this fragment
        createAccountBinding.viewModel = createAccountViewModel
        observeEvents()
        return createAccountBinding.root
        // Inflate the layout for this fragment
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            createAccountViewModel.events.observe(viewLifecycleOwner) {
                when (it) {
                    CreateAccountEvents.OnBackPress -> {
                        try {
                            findNavController().popBackStack()
                        } catch (_: Exception) {
                        }
                    }
                }
            }
        }
    }
}