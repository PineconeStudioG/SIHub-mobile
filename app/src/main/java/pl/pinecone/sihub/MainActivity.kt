package pl.pinecone.sihub

import android.content.Context
import android.content.Intent
//import android.content.res.Configuration
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

        val errorIntent = Intent(applicationContext, ErrorActivity::class.java)

        if(!this.networkConnected())
            startActivity(errorIntent)

        this.appWebView = findViewById(R.id.webview)
        this.appWebView.webViewClient = object : WebViewClient()
        {
            @Deprecated("Ok")
            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?)
            {
                startActivity(errorIntent)
                super.onReceivedError(view, errorCode, description, failingUrl)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if(!networkConnected())
                    startActivity(errorIntent)
                super.onPageStarted(view, url, favicon)
            }
        }

        this.appWebViewSettings = this.appWebView.settings
        this.appWebViewSettings.javaScriptEnabled = true
        this.appWebViewSettings.javaScriptCanOpenWindowsAutomatically = true
        this.appWebViewSettings.supportMultipleWindows()
        this.appWebViewSettings.domStorageEnabled = true
        this.appWebViewSettings.loadWithOverviewMode = true
        this.appWebViewSettings.useWideViewPort = false
        this.appWebViewSettings.setSupportZoom(false)

        this.appWebView.loadUrl("https://www.sihubmobile.szyszyszyszka.pl")
    }

    @Deprecated("Ok")
    override fun onBackPressed()
    {
        if(this.appWebView.canGoBack())
            this.appWebView.goBack()
        else
            super.onBackPressed()
    }

    private fun networkConnected(): Boolean {
        val conMan : ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return conMan.activeNetworkInfo != null
    }

/*
    override fun onConfigurationChanged(newConfig: Configuration) {

        val injectionLight = "localStorage.setItem('theme', 'light'); implementSaved();"
        val injectionDark = "localStorage.setItem('theme', 'dark'); implementSaved();"
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                //val jsScript = "javascript:localStorage.setItem(\"theme\",\"light\");implementSaved();"
                //this.appWebView.evaluateJavascript(jsScript,null)
                this.appWebView.loadData(injectionLight, "text/javascript", null)
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                //val jsScript = "javascript:localStorage.setItem(\"theme\",\"dark\");implementSaved();"
                //this.appWebView.evaluateJavascript(jsScript,null)
                this.appWebView.loadData(injectionDark, "text/javascript", null)
            }
        }
        super.onConfigurationChanged(newConfig)
    }
*/
}