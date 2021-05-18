package com.example.imageshow.model

import com.example.imageshow.model.Photo
import com.example.imageshow.model.Urls
import com.example.imageshow.model.User
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by Ryu on 18,五月,2021
 */
class ModelTest{

    private lateinit var user: User
    private lateinit var urls: Urls
    private lateinit var photo: Photo


    @Before
    fun setUp() {
        user = User("mockUN","mockN")
        urls = Urls("mockFull","mockRow","mockRegular","mockSmall","mockThumb")
        photo = Photo("mockD","mockID",urls,user)
    }


    @Test
    fun userTest() {
        val userCopy = user.copy()
        assertEquals("mockUN" , userCopy.username)
    }

    @Test
    fun urlsTest() {
        val urlsCopy = urls.copy()
        assertEquals("mockFull",urlsCopy.full)
    }

    @Test
    fun photoTest() {
        val photoCopy = photo.copy()
        assertEquals("mockD",photoCopy.description)
        assertEquals("mockThumb",photoCopy.urls.thumb)
        assertEquals("mockN",photoCopy.user.name)
    }
}