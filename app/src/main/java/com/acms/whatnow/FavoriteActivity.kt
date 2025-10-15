package com.acms.whatnow

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acms.whatnow.models.Article
import com.acms.whatnow.models.Source
import com.google.firebase.firestore.FirebaseFirestore

class FavoriteActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val favoritesList = mutableListOf<Article>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorite)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerViewFavorites)

        db.collection("favorites").get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    val article = Article(
                        source = Source(null, "Firebase"),
                        title = doc.getString("title"),
                        description = doc.getString("description"),
                        urlToImage = doc.getString("urlToImage"),
                        url = null,
                        favorite = true
                    )
                    favoritesList.add(article)
                }
                recyclerView.adapter = NewsAdapter(favoritesList)
            }
    }
}
