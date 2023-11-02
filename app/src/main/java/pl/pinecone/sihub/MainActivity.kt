package pl.pinecone.sihub

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var appWebView : WebView
    private lateinit var appWebViewSettings : WebSettings
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val errorInetnt = Intent(applicationContext, ErrorActivity::class.java)

        if(!this.networkConnected())
            startActivity(errorInetnt)

        this.appWebView = findViewById(R.id.webview)
        this.appWebView.webViewClient = object : WebViewClient()
        {
            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?)
            {
                startActivity(errorInetnt)
                super.onReceivedError(view, errorCode, description, failingUrl)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if(!networkConnected())
                    startActivity(errorInetnt)
                super.onPageStarted(view, url, favicon)
            }
        }

        this.appWebViewSettings = this.appWebView.settings
        this.appWebViewSettings.javaScriptEnabled = true
        this.appWebViewSettings.javaScriptCanOpenWindowsAutomatically = true
        this.appWebViewSettings.supportMultipleWindows()
        this.appWebViewSettings.domStorageEnabled = true

        this.appWebView.loadUrl("https://www.sihubmobile.szyszyszyszka.pl")

    }

    override fun onBackPressed()
    {
        if(this.appWebView.canGoBack())
            this.appWebView.goBack()
        else
            super.onBackPressed()
    }

    /*
    override fun onConfigurationChanged(newConfig: Configuration) {
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                val jsScript = "javascript:localStorage.setItem(\"theme\",\"light\");implementSaved();"
                this.appWebView.evaluateJavascript(jsScript,null)
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                val jsScript = "javascript:localStorage.setItem(\"theme\",\"dark\");implementSaved();"
                this.appWebView.evaluateJavascript(jsScript,null)
            }
        }
        super.onConfigurationChanged(newConfig)
    }
     */

    private fun networkConnected(): Boolean {
        val conMan : ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return conMan.activeNetworkInfo != null
    }
}