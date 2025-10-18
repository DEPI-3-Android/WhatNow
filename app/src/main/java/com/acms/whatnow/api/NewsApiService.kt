package com.acms.whatnow.api

import com.acms.whatnow.models.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("category") category: String = "general",
        @Query("country") country: String = "us",
        @Query("max") pageSize: Int = 20,
        @Query("token") apiKey: String,
        @Query("in") content: String = "title,description,image"
    ): Call<NewsResponse>

    @GET("search")
    fun getNewsWithImages(
        @Query("q") query: String = "",
        @Query("country") country: String = "us",
        @Query("max") pageSize: Int = 20,
        @Query("token") apiKey: String,
        @Query("image") includeImages: String = "required"
    ): Call<NewsResponse>
}