package com.mockdroid.koran.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.webkit.*
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

        settingWebview()
        initWebView(url ?: "")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun settingWebview() {
        val webView = webview_news.settings
        webView.allowContentAccess = true
        webView.useWideViewPort = true
        webView.loadsImagesAutomatically = true
        webView.javaScriptEnabled = true
        webView.domStorageEnabled = true
        webView.setSupportZoom(true)
        webView.builtInZoomControls = true
        webView.displayZoomControls = false
        webView.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.setRenderPriority(WebSettings.RenderPriority.HIGH)
        webView.setEnableSmoothTransition(true)
        webView.domStorageEnabled = true
    }

    private fun initWebView(url: String) {
        if (Build.VERSION.SDK_INT >= 19) {
            webview_news.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        } else {
            webview_news.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        webview_news.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                pg_detail_news.visibility = View.VISIBLE
                pg_detail_news.progress = newProgress
                if (newProgress == 100) {
                    pg_detail_news.visibility = View.GONE
                }
                super.onProgressChanged(view, newProgress)
            }
        }
        webview_news.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, URL: String?): Boolean {
                view?.loadUrl(URL)
                pg_detail_news.visibility = View.GONE
                return true
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view?.loadUrl(request?.url.toString())
                }
                pg_detail_news.visibility = View.VISIBLE
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                pg_detail_news.visibility = View.GONE
            }
        }
        webview_news.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webview_news.loadUrl(url)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (webview_news.canGoBack()) {
            webview_news.goBack()
        } else {
            super.onBackPressed()
        }
    }
}