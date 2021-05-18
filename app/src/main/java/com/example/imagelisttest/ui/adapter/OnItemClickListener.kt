package com.example.imagelisttest.ui.adapter

import androidx.appcompat.widget.AppCompatImageView
import com.example.imagelisttest.model.Photo

/**
 * Created by Ryu on 17,五月,2021
 */
interface OnItemClickListener {
    fun onItemClick(item: Photo?,view: AppCompatImageView)
}