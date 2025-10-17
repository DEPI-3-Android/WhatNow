package com.acms.whatnow.models

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acms.whatnow.NewsAdapter
import com.acms.whatnow.R
import com.google.firebase.firestore.FirebaseFirestore

class FavoriteActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val favoritesList = mutableListOf<Article>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        recyclerView = findViewById(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch favorites from Firestore
        db.collection("favorites").get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    val article = Article(
                        source = Source(null, "Firebase"),
                        title = doc.getString("title"),
                        description = doc.getString("description"),
                        urlToImage = doc.getString("urlToImage"),
                        url = doc.getString("url"),
                        favorite = true
                    )
                    favoritesList.add(article)
                }
                recyclerView.adapter = NewsAdapter(favoritesList, db)
            }
    }
}
