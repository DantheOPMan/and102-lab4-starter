package com.codepath.articlesearch

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var overviewTextView: TextView
    private lateinit var languageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mediaImageView = findViewById(R.id.mediaImage)
        titleTextView = findViewById(R.id.mediaTitle)
        dateTextView = findViewById(R.id.mediaDate)
        overviewTextView = findViewById(R.id.mediaOverview)
        languageTextView = findViewById(R.id.mediaLanguage)

        val article = intent.getSerializableExtra(TVSHOW_EXTRA) as TVShow

        titleTextView.text = article.name
        dateTextView.text = article.first_air_date
        overviewTextView.text = article.overview
        languageTextView.text = article.original_language
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500" + article.backdrop_path)
            .into(mediaImageView)
    }
}