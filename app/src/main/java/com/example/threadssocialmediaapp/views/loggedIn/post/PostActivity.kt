package com.example.threadssocialmediaapp.views.loggedIn.post

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import com.example.threadssocialmediaapp.databinding.ActivityPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    private val postViewModel: PostViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        binding.viewModel = postViewModel
        setContentView(binding.root)
        observeEvents()
    }

    private fun observeEvents() {
        postViewModel.events.observe(this) {
            when (it) {
                PostEvents.CloseActivity->{
                    finish()
                }
                else -> {}
            }
        }
    }
}