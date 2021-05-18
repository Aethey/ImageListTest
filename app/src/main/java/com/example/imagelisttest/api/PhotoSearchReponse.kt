package com.example.imagelisttest.api

import com.example.imagelisttest.model.Photo
import com.example.imagelisttest.model.Urls
import com.example.imagelisttest.model.User

/**
 * Created by Ryu on 17,五月,2021
 */
data class PhotoSearchResponse(
    val total: Int,
    val total_pages: Int,
    val results: ArrayList<Photo>,
)