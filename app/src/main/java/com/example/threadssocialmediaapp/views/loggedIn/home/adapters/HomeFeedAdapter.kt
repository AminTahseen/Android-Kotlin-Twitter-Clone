package com.example.threadssocialmediaapp.views.loggedIn.home.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.threadssocialmediaapp.databinding.HomeFeedItemBinding
import com.example.threadssocialmediaapp.models.dto.PostDTO
import com.example.threadssocialmediaapp.utils.formatDateTime
import com.example.threadssocialmediaapp.utils.gone
import com.example.threadssocialmediaapp.utils.visible
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeFeedAdapter(
    val onImageClick: (imageURL: String) -> Unit,
    val onPostClick: (post: PostDTO.Post) -> Unit

) :
    RecyclerView.Adapter<HomeFeedAdapter.HomeFeedViewHolder>() {
    private val homeFeedList = ArrayList<PostDTO.Post>()
    private lateinit var binding: HomeFeedItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFeedViewHolder {
        binding = HomeFeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeFeedViewHolder(binding, onImageClick, onPostClick)
    }

    override fun getItemCount(): Int {
        return homeFeedList.size
    }

    override fun onBindViewHolder(holder: HomeFeedViewHolder, position: Int) {
        val item = homeFeedList[position]
        holder.bind(item)
    }

    fun addItems(list: List<PostDTO.Post>) {
        if (homeFeedList.containsAll(list).not()) {
            homeFeedList.addAll(list)
        }
        notifyDataSetChanged()
    }

    class HomeFeedViewHolder(
        private val binding: HomeFeedItemBinding,
        val onImageClick: (imageURL: String) -> Unit,
        val onPostClick: (obj: PostDTO.Post) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostDTO.Post) {
            binding.root.setOnClickListener {
                onPostClick(post)
            }
            val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            val fullName = "${post.owner?.firstName} ${post.owner?.lastName}"
            binding.userName.text = fullName
            binding.postDescription.text = post.text
            if (post.image?.isEmpty() == true) {
                binding.thumbnailCard.gone()
            } else {
                binding.thumbnailCard.visible()
                Glide.with(binding.root).load(post.image)
                    .placeholder(circularProgressDrawable)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.postImage)
            }
            Glide.with(binding.root).load(post.owner?.picture)
                .placeholder(circularProgressDrawable)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.profileImage)

            binding.likesTotal.text = "${post.likes} Likes"
            binding.timePosted.text = formatDateTime(post.publishDate!!)
            val tagsAdapter = post.tags?.let { TagsAdapter(tagsList = it) }
            binding.tagsRecycler.adapter = tagsAdapter

            binding.postImage.setOnClickListener {
                onImageClick(post.image.toString())
            }
        }


    }


}