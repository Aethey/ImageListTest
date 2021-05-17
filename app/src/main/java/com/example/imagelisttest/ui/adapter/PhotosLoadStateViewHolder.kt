package com.example.imagelisttest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.imagelisttest.databinding.PhotosLoadStateFooterViewItemBinding

class PhotosLoadStateViewHolder constructor(private val binding: PhotosLoadStateFooterViewItemBinding,
                                            retry: () -> Unit): RecyclerView.ViewHolder(binding.root){

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): PhotosLoadStateViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            val binding = PhotosLoadStateFooterViewItemBinding.inflate(inflater,parent,false)

            return PhotosLoadStateViewHolder(binding, retry)
        }
    }
}