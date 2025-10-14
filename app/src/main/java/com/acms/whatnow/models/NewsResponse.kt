package com.acms.whatnow.models

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val url: String?
)

data class Source(
    val id: String?,
    val name: String
)