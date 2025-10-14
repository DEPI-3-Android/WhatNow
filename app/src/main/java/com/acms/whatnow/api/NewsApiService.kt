package com.acms.whatnow.api

import com.acms.whatnow.models.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    fun getNewsByCountry(
        @Query("country") country: String = "us",
        @Query("category") category: String = "general",
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String = "0f14c4588914480ae3ef9b3ca684ca4"
    ): Call<NewsResponse>

}
