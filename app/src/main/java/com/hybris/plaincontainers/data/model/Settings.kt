package com.hybris.plaincontainers.data.model

import com.hybris.plaincontainers.data.SupportedLocale
import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val locale: SupportedLocale
) {}