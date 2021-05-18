package com.example.imageshow

import com.example.imageshow.model.Photo
import com.example.imageshow.model.Urls
import com.example.imageshow.model.User
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by Ryu on 18,五月,2021
 */
open class ModelTest{

    private lateinit var user: User
    private lateinit var urls: Urls
    open lateinit var photo: Photo


    @Before
    fun setUp() {
        user = User("mockUN","mockN")
        urls = Urls("mockFull","mockRow","mockRegular","mockSmall","mockThumb")
        photo = Photo("mockD","mockID",urls,user)
    }


    @Test
    fun userTest() {
        val userCopy = user.copy()
        assertEquals(user.username , userCopy.username)
    }

    @Test
    fun urlsTest() {
        val urlsCopy = urls.copy()
        assertEquals(urls.full,urlsCopy.full)
    }

    @Test
    fun photoTest() {
        val photoCopy = photo.copy()
        assertEquals(photo.description,photoCopy.description)
        assertEquals(photo.urls.regular,photoCopy.urls.regular)
        assertEquals(photo.urls.thumb,photoCopy.urls.thumb)
        assertEquals(photo.user.name,photoCopy.user.name)
    }
}