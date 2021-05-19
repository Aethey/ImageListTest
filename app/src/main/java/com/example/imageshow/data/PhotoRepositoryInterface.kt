package com.example.imageshow.data

import androidx.paging.PagingData
import com.example.imageshow.model.Photo
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ryu on 19,五月,2021
 */
interface PhotoRepositoryInterface {

    fun getListPhotoStream(terms: String?): Flow<PagingData<Photo>>
}