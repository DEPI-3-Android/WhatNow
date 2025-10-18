package com.acms.whatnow.models

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val content: String? = null,
    val url: String? = null,
    @SerializedName("image") val urlToImage: String? = null,
    val publishedAt: String? = null,
    val lang: String? = null,
    val source: Source? = null,
    var favorite: Boolean = false
)

data class Source(
    val id: String? = null,
    val name: String? = null,
    val url: String? = null,
    val country: String? = null
)
