package com.example.threadssocialmediaapp.views.loggedIn.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.threadssocialmediaapp.databinding.HomeFeedItemBinding
import com.example.threadssocialmediaapp.databinding.TagItemBinding
import com.example.threadssocialmediaapp.models.dto.PostDTO

class TagsAdapter(private val tagsList: List<String>) :
    RecyclerView.Adapter<TagsAdapter.TagsViewHolder>() {
    private lateinit var binding: TagItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        binding = TagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tagsList.size
    }

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {
        val item = tagsList[position]
        holder.bind(item)
    }

    class TagsViewHolder(
        private val binding: TagItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: String) {
            binding.tagContent.text = "#$tag"
        }
    }
}