package com.example.imagelisttest.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.imagelisttest.Config
import com.example.imagelisttest.api.PhotoService
import com.example.imagelisttest.model.Photo
import kotlinx.coroutines.flow.Flow

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