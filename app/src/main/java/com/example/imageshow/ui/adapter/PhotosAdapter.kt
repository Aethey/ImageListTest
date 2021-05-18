package com.example.imageshow.ui.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.imageshow.R
import com.example.imageshow.databinding.PhotoItemBinding
import com.example.imageshow.model.Photo

/**
 * Created by Ryu on 15,五月,2021
 */
class PhotosAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Photo, PhotosAdapter.PhotoListViewHolder>(REPO_COMPARATOR) {

    /// item view type,0:List 1: Gird
    private var currentItemType = 0

    fun setItemType(itemType: Int) {
        currentItemType = itemType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = PhotoItemBinding.inflate(inflater, parent, false)
        return PhotoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        val photo = getItem(position)
        if (photo != null) {
            if (currentItemType == 0) {
                holder.binding.username.visibility = VISIBLE
                holder.binding.description.visibility = VISIBLE
                holder.binding.username.text = holder.binding.root.context.getString(
                    R.string.photo_user_name,
                    photo.user.username
                )
                holder.binding.description.text = photo.description ?: "There is no description"
            } else {
                holder.binding.username.visibility = GONE
                holder.binding.description.visibility = GONE
            }
            Glide.with(holder.itemView.context)
                .apply {
                    // Set of available caching strategies for image.
                    RequestOptions().skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL)
                }
                .load(photo.urls.thumb)
                .into(holder.binding.itemImageview)
        }
        holder.binding.root.setOnClickListener {
            listener.onItemClick(photo, holder.binding.itemImageview)
        }

    }

    inner class PhotoListViewHolder(val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem == newItem
        }
    }

}


