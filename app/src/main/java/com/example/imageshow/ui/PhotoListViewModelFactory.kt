package com.example.imageshow.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imageshow.data.PhotoRepository
import java.lang.IllegalArgumentException

/**
 * Created by Ryu on 15,五月,2021
 */
class PhotoListViewModelFactory constructor(private val repository: PhotoRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PhotoListViewModel::class.java)) {
            PhotoListViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }


}