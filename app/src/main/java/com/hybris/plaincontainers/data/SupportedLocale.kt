package com.hybris.plaincontainers.data

enum class SupportedLocale(private val bcp47LangTag: String) {
    EN("en"),
    DE("de");

    fun toLanguageTag(): String {
        return bcp47LangTag
    }
}