package com.acms.whatnow

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.acms.whatnow.R
import com.example.myapplication.Categories

class CategoriesAdapter(
    private val activity: Activity,
    private val categories: List<Categories>
) : RecyclerView.Adapter<CategoriesAdapter.MVH>() {

    class MVH(view: View) : RecyclerView.ViewHolder(view) {
        val parent: CardView = view.findViewById(R.id.card_categories)
        val imageView: ImageView = view.findViewById(R.id.categories_image)
        val textView: TextView = view.findViewById(R.id.categories_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MVH {
        val view = activity.layoutInflater.inflate(R.layout.layout_categories, parent, false)
        return MVH(view)
    }

    override fun onBindViewHolder(holder: MVH, position: Int) {
        val category = categories[position]

        holder.imageView.setImageResource(category.pic)
        holder.textView.text = category.name

        holder.parent.setOnClickListener {
            val intent = Intent(activity, NewsActivity::class.java)
            intent.putExtra("category", category.name.lowercase())
            activity.startActivity(intent)
        }
    }

    override fun getItemCount() = categories.size
}
