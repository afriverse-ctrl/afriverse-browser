package org.afriverse.browser

import android.app.Application
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoRuntimeSettings

class AfriverseApp : Application() {

    val runtime: GeckoRuntime by lazy {
        val settings = GeckoRuntimeSettings.Builder()
            .aboutConfigEnabled(false)
            .consoleOutput(false)
            .remoteDebuggingEnabled(false)
            .build()
        GeckoRuntime.create(this, settings)
    }
}
