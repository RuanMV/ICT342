package com.ict342.android

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_fullscreen.*
import org.jetbrains.anko.doAsync

/**
 * A full-screen activity that hides the system UI (i.e.
 * status bar and navigation/system bar)
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.hide()

        webview.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        doAsync {
            runOnUiThread {
                // Enable javascript so the website functions
                webview.settings.javaScriptEnabled = true

                // Override url loading so that the default web browser
                // doesn't start when a link is clicked
                webview.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView,
                        url: String
                    ): Boolean {
                        //view.loadUrl(url);
                        view.loadUrl(url)
                        return true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        webview.visibility = View.VISIBLE
                        progress_bar.visibility = View.GONE
                    }
                }

                // Load the URL for the website
                webview.loadUrl("http://www.ict342.rf.gd/wordpress")
            }
        }
    }

    /**
     * Called when the back key is pressed on the key bar.
     */
    override fun onBackPressed() {
        // If the webview has a previous page, go back.
        if (webview.canGoBack())
            webview.goBack()
        else //  If the webview has no previous page, let the system handle the back function.
            super.onBackPressed()
    }
}
