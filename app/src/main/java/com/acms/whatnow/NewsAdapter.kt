package com.acms.whatnow

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.acms.whatnow.models.Article
import com.google.firebase.firestore.FirebaseFirestore

class NewsAdapter(private val articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val db = FirebaseFirestore.getInstance()
    private val favoritesRef = db.collection("favorites")

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.newsTitle)
        val description: TextView = itemView.findViewById(R.id.newsDescription)
        val image: ImageView = itemView.findViewById(R.id.newsImage)
        val star: ImageView = itemView.findViewById(R.id.star_fab)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        holder.description.text = article.description ?: ""

        Glide.with(holder.itemView.context)
            .load(article.urlToImage)
            .placeholder(R.drawable.ic_news)
            .into(holder.image)


        // Check if this article is already a favorite
        favoritesRef.document(article.title ?: "no_title").get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    article.favorite = true
                    holder.star.setColorFilter(Color.YELLOW)
                } else {
                    article.favorite = false
                    holder.star.setColorFilter(Color.WHITE)
                }
            }

        // Open article in WebView when clicked
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, WebViewActivity::class.java)
            intent.putExtra("url", article.url)
            holder.itemView.context.startActivity(intent)
        }

        // Handle favorite toggle
        holder.star.setOnClickListener {
            article.favorite = !article.favorite

            if (article.favorite) {
                // Add this article to favorites
                val favItem = mapOf(
                    "title" to article.title,
                    "description" to article.description,
                    "urlToImage" to article.urlToImage
                )
                favoritesRef.document(article.title ?: "no_title").set(favItem)
                holder.star.setColorFilter(Color.YELLOW)
            } else {
                // Remove this article from favorites
                favoritesRef.document(article.title ?: "no_title").delete()
                holder.star.setColorFilter(Color.WHITE)
            }
        }

    }

    override fun getItemCount() = articles.size
}
