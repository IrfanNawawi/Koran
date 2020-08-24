package com.mockdroid.koran.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mockdroid.koran.R
import com.mockdroid.koran.model.ArticlesItem
import com.mockdroid.koran.utils.DateFormat
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val getData = intent.getParcelableExtra<ArticlesItem>("data")
        val url = getData?.url

        tv_title_appbar.text = getData?.source?.name
        tv_subtitle_appbar.text = getData?.url
        tv_title.text = getData?.title
        tv_time.text = getData?.author + " \u2022 " + DateFormat.getShortDate(getData?.publishedAt)
        Glide.with(this)
            .load(getData?.urlToImage ?: 0)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(img_detail_news)

        initWebView(url ?: "")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(url: String) {
        val webView: WebView = findViewById(R.id.webview_news)
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}