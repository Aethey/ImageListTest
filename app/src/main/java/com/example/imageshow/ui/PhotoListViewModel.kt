package com.example.imageshow.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.imageshow.data.PhotoRepository
import com.example.imageshow.model.Photo
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Ryu on 15,五月,2021
 */
class PhotoListViewModel constructor(private val photoRepository: PhotoRepository) : ViewModel() {
    /// current search terms
    private var currentTerms: String? = null

    /// current search terms result,if next search is same, return this
    private var currentSearchResult: Flow<PagingData<Photo>>? = null

    fun getPhotoList(terms: String?): Flow<PagingData<Photo>> {
        val lastResult = currentSearchResult

        if (terms != null) {
            if (currentTerms == terms && lastResult != null) {
                return lastResult
            }
            currentTerms = terms
        }

        val newResult = photoRepository.getListPhotoStream(terms)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult

        return newResult
    }

}