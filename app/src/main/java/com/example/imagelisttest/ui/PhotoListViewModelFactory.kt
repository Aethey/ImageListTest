package com.example.imagelisttest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imagelisttest.data.PhotoRepository
import java.lang.IllegalArgumentException

class PhotoListViewModelFactory constructor(private val repository: PhotoRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PhotoListViewModel::class.java)){
            PhotoListViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }


}