package com.ironmeddie.notes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig

@HiltAndroidApp
class NoteApp: Application() {

    override fun onCreate() {
        super.onCreate()
        val config = AppMetricaConfig.newConfigBuilder("key").build()
        AppMetrica.activate(this, config)
        AppMetrica.enableActivityAutoTracking(this)
    }
}
