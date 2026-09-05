package org.afriverse.browser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import org.afriverse.browser.databinding.ActivityMainBinding
import org.mozilla.geckoview.GeckoResult
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoSessionSettings

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var session: GeckoSession
    private var currentUrl: String = BuildConfig.HOMEPAGE_URL
    private var canGoBack: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val runtime = (application as AfriverseApp).runtime

        session = GeckoSession(
            GeckoSessionSettings.Builder()
                .usePrivateMode(false)
                .build()
        )
        session.open(runtime)
        binding.geckoView.setSession(session)

        session.navigationDelegate = object : GeckoSession.NavigationDelegate {
            override fun onLocationChange(
                session: GeckoSession,
                url: String?,
                perms: MutableList<GeckoSession.PermissionDelegate.ContentPermission>,
                hasUserGesture: Boolean
            ) {
                url?.let {
                    currentUrl = it
                    binding.urlBar.setText(it)
                }
            }

            override fun onCanGoBack(session: GeckoSession, canGoBack: Boolean) {
                this@MainActivity.canGoBack = canGoBack
            }
        }

        session.progressDelegate = object : GeckoSession.ProgressDelegate {
            override fun onProgressChange(session: GeckoSession, progress: Int) {
                binding.progressBar.progress = progress
                binding.progressBar.visibility = if (progress in 1..99) {
                    android.view.View.VISIBLE
                } else {
                    android.view.View.GONE
                }
            }
        }

        binding.urlBar.setOnEditorActionListener { view, actionId, event ->
            val submit = actionId == EditorInfo.IME_ACTION_GO ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                (event?.action == KeyEvent.ACTION_DOWN &&
                    event.keyCode == KeyEvent.KEYCODE_ENTER)
            if (submit) {
                loadUrlFromInput(view.text.toString())
                true
            } else {
                false
            }
        }

        binding.reloadBtn.setOnClickListener { session.reload() }

        val initial = handleViewIntent(intent) ?: BuildConfig.HOMEPAGE_URL
        session.loadUri(initial)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (canGoBack) {
                    session.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleViewIntent(intent)?.let { session.loadUri(it) }
    }

    private fun handleViewIntent(intent: Intent?): String? {
        if (intent?.action == Intent.ACTION_VIEW) {
            val data: Uri? = intent.data
            if (data != null) return data.toString()
        }
        return null
    }

    private fun loadUrlFromInput(raw: String) {
        val trimmed = raw.trim()
        if (trimmed.isEmpty()) return
        val url = if (trimmed.contains("://")) {
            trimmed
        } else if (trimmed.contains(".") && !trimmed.contains(" ")) {
            "https://$trimmed"
        } else {
            "https://duckduckgo.com/?q=" + Uri.encode(trimmed)
        }
        session.loadUri(url)
    }

    override fun onDestroy() {
        session.close()
        super.onDestroy()
    }
}
