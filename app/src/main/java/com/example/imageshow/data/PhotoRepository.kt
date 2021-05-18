package com.example.imageshow.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.imageshow.Config
import com.example.imageshow.api.PhotoService
import com.example.imageshow.model.Photo
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ryu on 15,五月,2021
 */
class PhotoRepository constructor(private val photoService: PhotoService) {

    fun getListPhotoStream(terms: String?): Flow<PagingData<Photo>> {

        return if (terms == null) {
            Pager(
                config = PagingConfig(
                    pageSize = Config.NETWORK_PAGE_SIZE,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { PhotoPagingSource(photoService) }
            ).flow
        } else {
            Pager(
                config = PagingConfig(
                    pageSize = Config.NETWORK_PAGE_SIZE,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { PhotoSearchPagingSource(photoService, terms) }
            ).flow
        }
    }
}