package com.acms.whatnow.models

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val source: Source? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    @SerializedName("urlToImage") val urlToImage: String? = null,
    @SerializedName("publishedAt") val publishedAt: String? = null, // Added this field
    var favorite: Boolean = false
)

data class Source(
    val id: String? = null,
    val name: String? = null
)