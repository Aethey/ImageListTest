package com.example.imageshow

import com.example.imageshow.api.PhotoListResponse
import com.example.imageshow.api.PhotoSearchResponse
import com.example.imageshow.api.PhotoService
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.InputStreamReader

/**
 * Created by Ryu on 18,五月,2021
 */

@ExperimentalCoroutinesApi
class ApiServiceTest {

    private lateinit var photoService: PhotoService

    @Before
    fun setUp() {
        photoService = PhotoService.getInstance()
    }

    @Test
    fun `read sample success json file`() {
        val reader =
            InputStreamReader(this.javaClass.classLoader?.getResourceAsStream("get_success_response.json"))
        val json = reader.readText()
        reader.close()
        Assert.assertNotNull(json)

    }

    /**
     *  test photos
     *  coroutine UnitTest
     *  use runBlocking because https://github.com/Kotlin/kotlinx.coroutines/issues/1204
     */
    @Test
    fun testGetListPhotos() = runBlocking {
        val reader: InputStreamReader =
            InputStreamReader(this.javaClass.classLoader?.getResourceAsStream("get_success_response.json"))
        // json
        val mockResponse = Gson().fromJson(
            reader,
            PhotoListResponse::class.java
        )
        reader.close()
        // api
        val getListPhotosResponse = photoService.getListPhotos(1, 10, "latest")
        // success
        Assert.assertEquals(mockResponse.size, getListPhotosResponse.size)

    }

    /**
     * test search photos
     * terms: Android
     */
    @Test
    fun testSearchListPhotos() = runBlocking {
        val reader: InputStreamReader =
            InputStreamReader(this.javaClass.classLoader?.getResourceAsStream("search_success_response.json"))
        // json
        val mockResponse = Gson().fromJson(
            reader,
            PhotoSearchResponse::class.java
        )
        reader.close()
        // api
        val searchListPhotosResponse = photoService.searchListPhotos(1, 10, "latest")

        // success
//        val index = (0..9).random()
//        assertEquals(true, searchListPhotosResponse.results[index].description.contains("android",ignoreCase = true))
        Assert.assertEquals(mockResponse.results.size, searchListPhotosResponse.results.size)
        Assert.assertEquals(true, searchListPhotosResponse.total >= 0)
        Assert.assertEquals(true, searchListPhotosResponse.total_pages >= 0)
    }

}