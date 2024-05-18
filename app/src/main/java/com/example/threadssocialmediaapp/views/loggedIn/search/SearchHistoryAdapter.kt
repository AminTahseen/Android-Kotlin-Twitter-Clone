package com.example.threadssocialmediaapp.views.loggedIn.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.threadssocialmediaapp.databinding.SearchRecentListItemBinding
import com.example.threadssocialmediaapp.models.local.model.SearchHistoryItem

class SearchHistoryAdapter(
    val onItemClick: (tag: String) -> Unit,
    val onShowMenuClick: (id: Int,view:View) -> Unit

) :
    RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {
    private val searchHistoryList = ArrayList<SearchHistoryItem>()
    private lateinit var searchRecentListItemBinding: SearchRecentListItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        searchRecentListItemBinding =
            SearchRecentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHistoryViewHolder(searchRecentListItemBinding, onItemClick, onShowMenuClick)
    }

    override fun getItemCount(): Int {
        return searchHistoryList.size
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val item = searchHistoryList[position]
        holder.bind(item)
    }

    fun addItems(list: List<SearchHistoryItem>) {
        if (searchHistoryList.containsAll(list).not()) {
            searchHistoryList.addAll(list)
        }
        notifyDataSetChanged()
    }

    class SearchHistoryViewHolder(
        val binding: SearchRecentListItemBinding,
        val onItemClick: (tag: String) -> Unit,
        val onShowMenuClick: (id: Int,view:View) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(searchItem: SearchHistoryItem) {
            binding.searchContent.text = "#${searchItem.searchHistoryTag}"
            binding.onDate.text = searchItem.searchedHistoryDate
            binding.clickableContent.setOnClickListener {
                onItemClick(searchItem.searchHistoryTag)
            }
            binding.menu.setOnClickListener {
                searchItem.id?.let { it1 -> onShowMenuClick(it1,it) }
            }
        }
    }
}