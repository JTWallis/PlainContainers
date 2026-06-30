package com.hybris.plaincontainers.data

import android.content.Context
import android.os.Build
import androidx.preference.PreferenceManager
import java.util.Locale
import androidx.core.content.edit

object LocaleUtils {

    private const val SELECTED_LANG = "Locale.Helper.Selected.Language"

    fun setLocale(context: Context, supportedLocale: SupportedLocale): Context {
        val language = supportedLocale.toLanguageTag()
        persist(context, language)
        return updateResources(context, language)
    }

    fun getLocale(context: Context): Locale {
        val lang = PreferenceManager
            .getDefaultSharedPreferences(context)
            .getString(SELECTED_LANG, Locale.getDefault().language)!!
        return Locale.forLanguageTag(lang)
    }

    fun getLocaleFromLanguageTag(languageTag: String): SupportedLocale {
        for(locale in SupportedLocale.entries) {
            if(languageTag == locale.toLanguageTag()) return locale
        }

        return SupportedLocale.EN
    }

    private fun persist(context: Context, lang: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit {
                putString(SELECTED_LANG, lang)
            }
    }

    private fun updateResources(context: Context, lang: String): Context {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        return context.createConfigurationContext(config)
    }
}