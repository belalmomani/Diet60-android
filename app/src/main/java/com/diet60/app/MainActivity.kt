package com.diet60.app

import android.annotation.SuppressLint
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var refresh: SwipeRefreshLayout
    private lateinit var startUrl: String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startUrl = getString(R.string.start_url)

        webView = WebView(this)
        refresh = SwipeRefreshLayout(this).apply { addView(webView) }
        setContentView(refresh)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true          // مطلوب لـ LocalStorage (حفظ التقدم)
            databaseEnabled = true
            cacheMode = WebSettings.LOAD_DEFAULT
            loadWithOverviewMode = true
            useWideViewPort = true
            setSupportZoom(false)
        }

        val host = Uri.parse(startUrl).host ?: ""

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val uri = request.url
                // روابط الموقع تبقى داخل التطبيق، وأي رابط خارجي (مصادر علمية) يُفتح في المتصفح
                return if (uri.host == host) {
                    false
                } else {
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                    true
                }
            }

            override fun onPageFinished(view: WebView, url: String?) {
                refresh.isRefreshing = false
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                if (request.isForMainFrame) {
                    refresh.isRefreshing = false
                    Toast.makeText(this@MainActivity, R.string.offline_msg, Toast.LENGTH_LONG).show()
                }
            }
        }

        refresh.setOnRefreshListener { webView.reload() }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) webView.goBack() else finish()
            }
        })

        if (savedInstanceState != null) webView.restoreState(savedInstanceState) else webView.loadUrl(startUrl)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }
}
