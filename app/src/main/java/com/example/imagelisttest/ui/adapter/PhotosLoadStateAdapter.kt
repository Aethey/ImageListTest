package com.example.imagelisttest.ui.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

/**
 * Created by Ryu on 16,五月,2021
 */
class PhotosLoadStateAdapter constructor(private val retry: () -> Unit) :
    LoadStateAdapter<PhotosLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: PhotosLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PhotosLoadStateViewHolder {
        return PhotosLoadStateViewHolder.create(parent, retry)
    }
}