package com.mockdroid.koran.view

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mockdroid.koran.BuildConfig
import com.mockdroid.koran.R
import com.mockdroid.koran.model.ArticlesItem
import com.mockdroid.koran.model.CategoryMenu
import com.mockdroid.koran.model.TopHeadlineResponse
import com.mockdroid.koran.network.Network
import com.mockdroid.koran.view.adapter.CategoryAdapter
import com.mockdroid.koran.view.adapter.TopHeadlineAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listCategory = ArrayList<CategoryMenu>()
        listCategory.add(CategoryMenu(R.drawable.ic_bussiness, "Business"))
        listCategory.add(CategoryMenu(R.drawable.ic_entertainment, "Entertainemnt"))
        listCategory.add(CategoryMenu(R.drawable.ic_health, "Health"))
        listCategory.add(CategoryMenu(R.drawable.ic_science, "Science"))
        listCategory.add(CategoryMenu(R.drawable.ic_sports, "Sports"))
        listCategory.add(CategoryMenu(R.drawable.ic_tech, "Technology"))

        rv_categories_menu.setHasFixedSize(true)
        rv_categories_menu.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_categories_menu.adapter = CategoryAdapter(listCategory)

        // Check Connection
        if (isConnect()) {

            Network.getRetrofit()
                .getTopHeadline("id", BuildConfig.APIKEY)
                .enqueue(object : Callback<TopHeadlineResponse> {
                    override fun onFailure(call: Call<TopHeadlineResponse>, t: Throwable) {
                        pg_news.visibility = View.GONE
                        Log.d("error server", t.message)
                    }

                    override fun onResponse(
                        call: Call<TopHeadlineResponse>,
                        response: Response<TopHeadlineResponse>
                    ) {
                        Log.d("response server", response.message())

                        //check response server
                        if (response.isSuccessful) {
                            pg_news.visibility = View.GONE
                            val status = response.body()?.status

                            if (status == "ok") {
                                val data = response.body()?.articles
                                showData(data)
                            }
                        }
                    }

                })
        } else {
            pg_news.visibility = View.GONE
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show()
        }
    }

    fun isConnect(): Boolean {
        val connect: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connect.activeNetworkInfo != null && connect.activeNetworkInfo.isConnected
    }

    private fun showData(data: List<ArticlesItem>?) {
        rv_news.adapter = TopHeadlineAdapter(data)
    }
}