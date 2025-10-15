package com.acms.whatnow

import android.os.Bundle
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

class NewsActivity : AppCompatActivity() {
    private val apiKey = "0f14c4588914480eae3ef9b3ca684ca4"
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        recyclerView = findViewById(R.id.newsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchCountryNews("us", "general")
    }

    private fun fetchCountryNews(country: String, category: String) {
        val api = ApiClient.instance.create(NewsApiService::class.java)
        api.getNewsByCountry(country, category, 20, apiKey)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful) {
                        val newsList = response.body()?.articles ?: emptyList()
                        Toast.makeText(
                            this@NewsActivity,
                            "News: ${newsList.size}",
                            Toast.LENGTH_SHORT
                        ).show()
                        recyclerView.adapter = NewsAdapter(newsList)
                    } else {
                        Toast.makeText(
                            this@NewsActivity,
                            "API Error ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Toast.makeText(this@NewsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}
