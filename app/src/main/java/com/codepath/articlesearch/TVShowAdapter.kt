package com.codepath.articlesearch

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val TVSHOW_EXTRA = "ARTICLE_EXTRA"
private const val TAG = "ArticleAdapter"

class ArticleAdapter(private val context: Context, private val articles: List<DisplayArticle>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tvshow, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount() = articles.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val mediaImageView = itemView.findViewById<ImageView>(R.id.mediaImage)
        private val mediaTitleView = itemView.findViewById<TextView>(R.id.mediaTitle)

        init {
            itemView.setOnClickListener(this)
        }
        fun bind(article: DisplayArticle) {
            titleTextView.text = article.headline
            abstractTextView.text = article.abstract

            Glide.with(context)
                .load(article.mediaImageUrl)
                .into(mediaImageView)
        }
        // TODO: Write a helper method to help set up the onBindViewHolder method

        override fun onClick(v: View?) {
            // Get selected article
            val article = articles[absoluteAdapterPosition]

            //  Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(ARTICLE_EXTRA, article)
            context.startActivity(intent)
        }
        fun bind(article: TVShow) {
            mediaTitleView.text = article.name

            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + article.poster_path)
                .into(mediaImageView)
        }
    }
}
