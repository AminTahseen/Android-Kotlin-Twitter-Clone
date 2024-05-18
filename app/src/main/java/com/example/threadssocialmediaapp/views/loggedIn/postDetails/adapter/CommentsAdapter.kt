package com.example.threadssocialmediaapp.views.loggedIn.postDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.threadssocialmediaapp.databinding.CommentsItemBinding
import com.example.threadssocialmediaapp.models.remote.dto.CommentsDTO
import com.example.threadssocialmediaapp.utils.formatDateTime


class CommentsAdapter :
    RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {
    private val commentsList = ArrayList<CommentsDTO.Comment>()
    private lateinit var binding: CommentsItemBinding

    fun addItems(list: List<CommentsDTO.Comment>) {
        if (commentsList.containsAll(list).not()) {
            commentsList.addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        binding = CommentsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(commentsList[position])
    }

    class CommentsViewHolder(
        private val binding: CommentsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentsDTO.Comment) {
            val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            binding.commentText.text = comment.message.toString().replaceFirstChar { it.uppercase() }
            val fullName = "${comment.owner?.firstName} ${comment.owner?.lastName}"
            binding.userName.text = fullName
            Glide.with(binding.root).load(comment.owner?.picture)
                .placeholder(circularProgressDrawable)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.profileImage)
            binding.timePosted.text = formatDateTime(comment.publishDate!!)
        }
    }
}