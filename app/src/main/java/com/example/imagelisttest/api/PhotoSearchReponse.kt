package com.example.imagelisttest.api

import com.example.imagelisttest.model.Photo
import com.example.imagelisttest.model.Urls
import com.example.imagelisttest.model.User

data class PhotoSearchResponse(
    val total: Int,
    val total_pages: Int,
    val results: ArrayList<Photo>,
)