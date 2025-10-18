package com.acms.whatnow

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.acms.whatnow.models.Article
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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
        val shareFab: FloatingActionButton = view.findViewById(R.id.share_fab)
        val cardView: androidx.cardview.widget.CardView = view.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]

        holder.title.text = article.title ?: "No Title"
        holder.description.text = article.description ?: "No description available"

        val imageUrl = article.urlToImage
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_news)
                .error(R.drawable.ic_news)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.ic_news)
        }

        updateStarAppearance(holder.starFab, article.favorite)

        holder.starFab.setOnClickListener {
            article.favorite = !article.favorite

            val docId = article.url?.hashCode()?.toString()
                ?: db.collection("favorites").document().id
            val favoritesRef = db.collection("favorites").document(docId)

            if (article.favorite) {
                val favoriteData = mapOf(
                    "title" to article.title,
                    "url" to article.url,
                    "image" to article.urlToImage
                )
                favoritesRef.set(favoriteData)
                updateStarAppearance(holder.starFab, true)
            } else {
                favoritesRef.delete()
                updateStarAppearance(holder.starFab, false)
            }
        }

        holder.itemView.setOnClickListener {
            article.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                holder.itemView.context.startActivity(intent)
            }
        }

        holder.shareFab.setOnClickListener {
            ShareCompat.IntentBuilder(holder.itemView.context)
                .setType("text/plain")
                .setChooserTitle("Share article with:")
                .setText(article.url)
                .startChooser()
        }
    }

    private fun updateStarAppearance(starFab: FloatingActionButton, isFavorite: Boolean) {
        val context = starFab.context
        if (isFavorite) {
            starFab.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_orange_dark))
            starFab.backgroundTintList =
                ContextCompat.getColorStateList(context, android.R.color.holo_orange_light)
        } else {
            starFab.setColorFilter(ContextCompat.getColor(context, android.R.color.black))
            starFab.backgroundTintList =
                ContextCompat.getColorStateList(context, android.R.color.darker_gray)
        }
    }

    override fun getItemCount(): Int = articles.size
}
