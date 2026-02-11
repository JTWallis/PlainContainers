package com.hybris.plaincontainers.data

import com.hybris.plaincontainers.data.model.Settings

object SettingsManager {
    private lateinit var settings: Settings
    private var isInit = false

    fun init(settings: Settings?) {
        if(settings == null) {
            this.settings = Settings(
                SupportedLocale.EN
            )
        } else {
            this.settings = settings
        }

        isInit = true
    }

    fun getSettings(): Settings {
        verifyInit()
        return settings
    }

    fun setLocale(locale: SupportedLocale) {
        settings = Settings(
            locale
        )

        JsonManager.writeSettings(settings)
    }

    fun getLocale(): SupportedLocale {
        return settings.locale
    }

    private fun verifyInit() {
        if(!isInit || !::settings.isInitialized) {
            throw NullPointerException("SettingsManager not initialized!")
        }
    }

}