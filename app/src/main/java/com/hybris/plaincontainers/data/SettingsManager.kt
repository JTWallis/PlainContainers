package com.hybris.plaincontainers.data

import com.hybris.plaincontainers.data.model.Settings

/**
 * Manager to read and edit Settings data, that are automatically persisted after change.
 * Must call init before use!
 */
object SettingsManager {
    private lateinit var settings: Settings
    private var isInit = false

    fun init(settings: Settings?) {
        if(settings == null) {
            this.settings = createSettingsDefault()
        } else {
            this.settings = settings
        }

        isInit = true
    }

    private fun createSettingsDefault(
        locale: SupportedLocale = SupportedLocale.EN,
        dragEnabled: Boolean = true,
        itemCountZeroBehavior: ItemCountZeroBehavior = ItemCountZeroBehavior.ASK
    ): Settings {
        return Settings(locale, dragEnabled, itemCountZeroBehavior)
    }

    private fun createSettingsCopy(
        other: Settings,
        locale: SupportedLocale = other.locale,
        dragEnabled: Boolean = other.dragEnabled,
        itemCountZeroBehavior: ItemCountZeroBehavior = other.itemCountZeroBehavior
    ): Settings {
        return Settings(locale, dragEnabled, itemCountZeroBehavior)
    }

    private fun writeSettings() {
        JsonManager.writeSettings(settings)
    }

    fun getSettings(): Settings {
        verifyInit()
        return settings
    }

    fun setLocale(locale: SupportedLocale) {
        verifyInit()
        if(locale == settings.locale) return

        settings = createSettingsCopy(settings, locale = locale)
        writeSettings()
    }

    fun getLocale(): SupportedLocale {
        verifyInit()
        return settings.locale
    }

    fun setDragEnabled(isEnabled: Boolean) {
        verifyInit()
        if(isEnabled == settings.dragEnabled) return

        settings = createSettingsCopy(settings, dragEnabled = isEnabled)
        writeSettings()
    }

    fun isDragEnabled(): Boolean {
        verifyInit()
        return settings.dragEnabled
    }

    fun setItemCountZeroBehavior(behavior: ItemCountZeroBehavior) {
        verifyInit()
        if(settings.itemCountZeroBehavior == behavior) return

        settings = createSettingsCopy(settings, itemCountZeroBehavior = behavior)
        writeSettings()
    }

    fun getItemCountZeroBehavior(): ItemCountZeroBehavior {
        verifyInit()
        return settings.itemCountZeroBehavior
    }

    private fun verifyInit() {
        if(!isInit || !::settings.isInitialized) {
            throw NullPointerException("SettingsManager not initialized!")
        }
    }

}