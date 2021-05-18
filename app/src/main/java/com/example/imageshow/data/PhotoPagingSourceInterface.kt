package com.example.imageshow.data

import androidx.paging.PagingSource
import com.example.imageshow.model.Photo

/**
 * Created by Ryu on 19,五月,2021
 */
interface PhotoPagingSourceInterface {

    fun getPagingSourceData(): PagingSource<Int, Photo>
}