package com.hybris.plaincontainers.data

import com.hybris.plaincontainers.data.model.Settings

object SettingsManager {
    private lateinit var settings: Settings
    private var isInit = false

    fun init(settings: Settings?) {
        if(settings == null) {
            this.settings = createSettings()
        } else {
            this.settings = settings
        }

        isInit = true
    }

    private fun createSettings(
        locale: SupportedLocale = SupportedLocale.EN,
        dragEnabled: Boolean = true
    ): Settings {
        return Settings(locale, dragEnabled)
    }

    private fun writeSettings() {
        JsonManager.writeSettings(settings)
    }

    fun getSettings(): Settings {
        verifyInit()
        return settings
    }

    fun setLocale(locale: SupportedLocale) {
        settings = createSettings(locale = locale)
        writeSettings()
    }

    fun getLocale(): SupportedLocale {
        return settings.locale
    }

    fun setDragEnabled(isEnabled: Boolean) {
        verifyInit()
        if(isEnabled == settings.dragEnabled) return

        settings = createSettings(dragEnabled = isEnabled)
        writeSettings()
    }

    fun isDragEnabled(): Boolean {
        verifyInit()
        return settings.dragEnabled
    }

    private fun verifyInit() {
        if(!isInit || !::settings.isInitialized) {
            throw NullPointerException("SettingsManager not initialized!")
        }
    }

}