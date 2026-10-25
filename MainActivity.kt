package com.drawabattle.tv

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView

private const val SITE = "https://drawabattle.netlify.app/?tv=1"

class MainActivity : Activity() {

    private lateinit var web: WebView

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        getWindow().decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        )

        web = WebView(this)
        setContentView(web)

        web.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = false
            mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        }
        web.webChromeClient = WebChromeClient()
        web.loadUrl(SITE)
    }

    private fun key(k: String, down: Boolean) {
        val t = if (down) "keydown" else "keyup"
        web.post {
            web.evaluateJavascript(
                "document.dispatchEvent(new KeyboardEvent('$t',{key:'$k',bubbles:true,cancelable:true}))",
                null
            )
        }
    }

    override fun dispatchKeyEvent(e: KeyEvent): Boolean {
        val down = e.action == KeyEvent.ACTION_DOWN
        when (e.keyCode) {
            KeyEvent.KEYCODE_DPAD_UP              -> { key("ArrowUp",    down); return true }
            KeyEvent.KEYCODE_DPAD_DOWN            -> { key("ArrowDown",  down); return true }
            KeyEvent.KEYCODE_DPAD_LEFT            -> { key("ArrowLeft",  down); return true }
            KeyEvent.KEYCODE_DPAD_RIGHT           -> { key("ArrowRight", down); return true }
            KeyEvent.KEYCODE_DPAD_CENTER,
            KeyEvent.KEYCODE_ENTER,
            KeyEvent.KEYCODE_NUMPAD_ENTER         -> { key("Enter",      down); return true }
            KeyEvent.KEYCODE_BACK                 -> { key("Escape",     down); return true }
            KeyEvent.KEYCODE_MENU,
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE     -> { key(" ",          down); return true }
        }
        return super.dispatchKeyEvent(e)
    }

    override fun onPause()   { super.onPause();   web.onPause()  }
    override fun onResume()  { super.onResume();  web.onResume() }
    override fun onDestroy() { super.onDestroy(); web.destroy()  }
}
