package com.mockdroid.koran.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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

        val appSettingPref: SharedPreferences = getSharedPreferences("AppSettingPrefs", 0)
        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPref.edit()
        val isNightModeOn: Boolean = appSettingPref.getBoolean("NightMode", false)

        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            img_mode.setImageResource(R.drawable.ic_day)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            img_mode.setImageResource(R.drawable.ic_night)
        }

        img_mode.setOnClickListener {
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                img_mode.setImageResource(R.drawable.ic_night)
                sharedPrefsEdit.putBoolean("NightMode", false)
                sharedPrefsEdit.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                img_mode.setImageResource(R.drawable.ic_day)
                sharedPrefsEdit.putBoolean("NightMode", true)
                sharedPrefsEdit.apply()
            }
        }

        val listCategory = ArrayList<CategoryMenu>()
        listCategory.add(CategoryMenu(R.drawable.ic_bussiness, "Business"))
        listCategory.add(CategoryMenu(R.drawable.ic_entertainment, "Entertainemnt"))
        listCategory.add(CategoryMenu(R.drawable.ic_health, "Health"))
        listCategory.add(CategoryMenu(R.drawable.ic_science, "Science"))
        listCategory.add(CategoryMenu(R.drawable.ic_sports, "Sports"))
        listCategory.add(CategoryMenu(R.drawable.ic_tech, "Technology"))

        rv_categories_menu.setHasFixedSize(true)
        rv_categories_menu.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_categories_menu.adapter = CategoryAdapter(listCategory)
        cv_search.setOnClickListener { Snackbar.make(it,R.string.coming_soon,Snackbar.LENGTH_SHORT).show() }

        //check connection
        if (isConnect()) {

            Network.getRetrofit()
                .getTopHeadline("id", BuildConfig.APIKEY)
                .enqueue(object : Callback<TopHeadlineResponse> {
                    override fun onFailure(call: Call<TopHeadlineResponse>, t: Throwable) {
                        pg_news.visibility = View.GONE
                        rv_news.visibility = View.GONE
                        ll_error.visibility = View.VISIBLE
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
            rv_news.visibility = View.GONE
            ll_error.visibility = View.VISIBLE
        }
    }

    fun isConnect(): Boolean {
        val connect: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connect.activeNetworkInfo != null && connect.activeNetworkInfo.isConnected
    }

    private fun showData(data: List<ArticlesItem>?) {
        rv_news.adapter = TopHeadlineAdapter(data, object : TopHeadlineAdapter.DetailClickListener {
            override fun detailData(item: ArticlesItem?) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("data", item)
                startActivity(intent)
            }
        })
    }


}