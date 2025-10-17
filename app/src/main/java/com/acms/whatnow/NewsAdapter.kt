package com.acms.whatnow

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.acms.whatnow.models.Article
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NewsAdapter(
    private val articles: List<Article>,
    private val db: FirebaseFirestore
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.articleTitle)
        val description: TextView = view.findViewById(R.id.articleDescription)
        val image: ImageView = view.findViewById(R.id.articleImage)
        val starFab: FloatingActionButton = view.findViewById(R.id.starFab)
        val cardView: androidx.cardview.widget.CardView = view.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]

        // Set title
        holder.title.text = article.title ?: "No Title"

        // Set description
        holder.description.text = article.description ?: "No description available"

        // Load image safely using Glide - FIXED: Proper Glide implementation
        val imageUrl = article.urlToImage
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_news)
                .error(R.drawable.ic_news)
                .into(holder.image)
        } else {
            // If no image URL, set placeholder
            holder.image.setImageResource(R.drawable.ic_news)
        }

        // Set star state using tint
        updateStarAppearance(holder.starFab, article.favorite)

        // Favorite toggle
        holder.starFab.setOnClickListener {
            article.favorite = !article.favorite
            val docId = article.url?.hashCode()?.toString() ?: db.collection("favorites").document().id
            val docRef = db.collection("favorites").document(docId)

            if (article.favorite) {
                docRef.set(article)
                updateStarAppearance(holder.starFab, true)
            } else {
                docRef.delete()
                updateStarAppearance(holder.starFab, false)
            }
        }

        // ADDED: Click listener for the entire card to open news article
        holder.itemView.setOnClickListener {
            article.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    private fun updateStarAppearance(starFab: FloatingActionButton, isFavorite: Boolean) {
        if (isFavorite) {
            // Filled state - use yellow tint
            starFab.setColorFilter(ContextCompat.getColor(starFab.context, android.R.color.holo_orange_dark))
            starFab.backgroundTintList = ContextCompat.getColorStateList(starFab.context, android.R.color.holo_orange_light)
        } else {
            // Outline state - use dark tint
            starFab.setColorFilter(ContextCompat.getColor(starFab.context, android.R.color.black))
            starFab.backgroundTintList = ContextCompat.getColorStateList(starFab.context, android.R.color.darker_gray)
        }
    }

    override fun getItemCount(): Int = articles.size
}