package com.example.imagelisttest.api

import com.example.imagelisttest.Config
import com.example.imagelisttest.model.Photo
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Unsplash API communication setup via Retrofit.
 * doc: https://unsplash.com/documentation#list-photos
 */
interface PhotoService {

    /**
     * default get photos
     * order_by use default latest
     *
     * page: Page number to retrieve. (Optional; default: 1)
     * per_page: Number of items per page. (Optional; default: 10)
     * order_by: How to sort the photos.
     */
    @GET("/photos")
    suspend fun getListPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String,
    ): PhotoListResponse

    /**
     * search photos
     * query: Search terms
     * page: Page number to retrieve. (Optional; default: 1)
     * per_page: Number of items per page. (Optional; default: 10)
     */
    @GET("/search/photos")
    suspend fun searchListPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("query") terms: String,
    ): PhotoSearchResponse

    companion object {
        var photoService: PhotoService? = null

        fun getInstance(): PhotoService {
            val logger = HttpLoggingInterceptor()
            logger.level = Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(
                    Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header("Authorization", "Client-ID " + Config.API_KEY)
                        return@Interceptor chain.proceed(builder.build())
                    }
                ).addInterceptor(logger)
                .build()

            if (photoService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Config.base_url)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                photoService = retrofit.create(PhotoService::class.java)
            }
            return photoService!!
        }
    }
}