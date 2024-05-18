package com.example.threadssocialmediaapp.views.loggedIn.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.threadssocialmediaapp.databinding.SearchRecentListItemBinding
import com.example.threadssocialmediaapp.models.dto.SearchHistoryItem

class SearchHistoryAdapter(private val searchHistoryList: List<SearchHistoryItem>) :
    RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {
    private lateinit var searchRecentListItemBinding: SearchRecentListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        searchRecentListItemBinding =
            SearchRecentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHistoryViewHolder(searchRecentListItemBinding)
    }

    override fun getItemCount(): Int {
        return searchHistoryList.size
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val item = searchHistoryList[position]
        holder.bind(item)
    }

    class SearchHistoryViewHolder(
        val binding: SearchRecentListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(searchItem: SearchHistoryItem) {
            binding.searchContent.text = searchItem.searchContent
            binding.onDate.text = searchItem.searchedOnDate
        }
    }
}