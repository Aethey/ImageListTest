package com.example.imageshow.model

/**
 * Created by Ryu on 15,五月,2021
 * unsplash Urls
 */
data class Photo(
    val description: String,
    val id: String,
    val urls: Urls,
    val user: User,
)