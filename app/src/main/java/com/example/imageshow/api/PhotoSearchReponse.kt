package com.example.imageshow.api

import com.example.imageshow.model.Photo
import com.example.imageshow.model.Urls
import com.example.imageshow.model.User

/**
 * Created by Ryu on 17,五月,2021
 */
data class PhotoSearchResponse(
    val total: Int,
    val total_pages: Int,
    val results: ArrayList<Photo>,
)