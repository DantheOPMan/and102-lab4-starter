package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.articlesearch.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = "c30b6be13072568f3198912087cdda39"
private const val ARTICLE_SEARCH_URL =
    "https://api.themoviedb.org/3/tv/top_rated?api_key=${SEARCH_API_KEY}"


class MainActivity : AppCompatActivity() {
    private val tvshows = mutableListOf<TVShow>()
    private lateinit var tvshowRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        tvshowRecyclerView = findViewById(R.id.tvshows)
        val articleAdapter =TVShowAdapter(this, tvshows)
        tvshowRecyclerView.adapter = articleAdapter
        val layoutManager = GridLayoutManager(this, 2)
        tvshowRecyclerView.layoutManager = layoutManager
        val client = AsyncHttpClient()
        client.get(ARTICLE_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    val parsedJson = createJson().decodeFromString(
                        SearchTvResponse.serializer(),
                        json.jsonObject.toString()
                    )

                    parsedJson.shows?.let { list ->
                        tvshows.addAll(list)
                    }

                    articleAdapter.notifyDataSetChanged()

                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }

        })

    }
}