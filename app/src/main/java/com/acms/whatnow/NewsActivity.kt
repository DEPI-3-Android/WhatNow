package com.acms.whatnow

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acms.whatnow.api.ApiClient
import com.acms.whatnow.api.NewsApiService
import com.acms.whatnow.models.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.firebase.firestore.FirebaseFirestore

class NewsActivity : AppCompatActivity() {
    private val apiKey = BuildConfig.NEWS_API_KEY // your API key for GNews
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        recyclerView = findViewById(R.id.newsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val category = intent.getStringExtra("category") ?: "general"
        val country = intent.getStringExtra("country") ?: "us"

        fetchTopHeadlines(category.lowercase(), country.lowercase())
    }

    private fun fetchTopHeadlines(category: String, country: String) {
        val api = ApiClient.instance.create(NewsApiService::class.java)
        api.getTopHeadlines(category, country, 20, apiKey)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (response.isSuccessful) {
                        val newsList = response.body()?.articles ?: emptyList()

                        // Detailed logging for debugging
                        Log.d("NewsActivity", "=== API RESPONSE DEBUG ===")
                        Log.d("NewsActivity", "Total articles: ${newsList.size}")

                        newsList.forEachIndexed { index, article ->
                            Log.d("NewsActivity", "Article $index:")
                            Log.d("NewsActivity", "  Title: ${article.title?.take(50)}...")
                            Log.d("NewsActivity", "  Image URL: ${article.urlToImage}")
                            Log.d("NewsActivity", "  URL: ${article.url}")
                            Log.d("NewsActivity", "  Has image: ${!article.urlToImage.isNullOrEmpty()}")

                            if (!article.urlToImage.isNullOrEmpty()) {
                                Log.d("NewsActivity", "  Image URL valid: ${article.urlToImage.startsWith("http")}")
                            }
                        }

                        if (newsList.isEmpty()) {
                            Toast.makeText(this@NewsActivity, "No news available", Toast.LENGTH_SHORT).show()
                        }

                        val db = FirebaseFirestore.getInstance()
                        val adapter = NewsAdapter(newsList, db)
                        recyclerView.adapter = adapter

                    } else {
                        Toast.makeText(this@NewsActivity, "API Error ${response.code()}", Toast.LENGTH_SHORT).show()
                        Log.e("NewsActivity", "API Error: ${response.code()} - ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Toast.makeText(this@NewsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    Log.e("NewsActivity", "API Call failed: ${t.message}")
                }
            })
    }
}