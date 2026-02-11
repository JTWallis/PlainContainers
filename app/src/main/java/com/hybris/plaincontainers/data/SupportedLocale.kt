package com.hybris.plaincontainers.data

import androidx.annotation.StringRes
import com.hybris.plaincontainers.R

enum class SupportedLocale(
    private val bcp47LangTag: String,
    @get:StringRes private val localizationId: Int) {
    EN("en", R.string.settings_general_language_en),
    DE("de", R.string.settings_general_language_de);

    fun toLanguageTag(): String {
        return bcp47LangTag
    }

    @StringRes fun toLocalizationId():  Int {
        return localizationId
    }
}