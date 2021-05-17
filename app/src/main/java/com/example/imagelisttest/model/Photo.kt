package com.example.imagelisttest.model

/**
 *
 */
data class Photo(
    val description: String,
    val id: String,
    val urls: Urls,
    val user: User,
)