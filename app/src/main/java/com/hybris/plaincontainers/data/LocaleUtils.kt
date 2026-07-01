package com.hybris.plaincontainers.data

import android.content.Context
import android.os.Build
import androidx.preference.PreferenceManager
import java.util.Locale
import androidx.core.content.edit

/**
 * Utils class to easily read and change the locale to a supported language from SupportedLocale.
 * A changed locale is automatically persisted.
 */
object LocaleUtils {

    private const val SELECTED_LANG = "Locale.Helper.Selected.Language"

    /**
     * Changes and persists the locale to the supported language.
     * If used outside of the MainActivity, the caller should also call requireActivity().recreate(),
     * to update the language during runtime.
     * @param context Context associated with the Fragment, usually received via requireContext()
     * @param supportedLocale Supported locale to change the app language to
     */
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
        val locale =
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) Locale.of(lang)
            else Locale(lang)

        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        return context.createConfigurationContext(config)
    }
}