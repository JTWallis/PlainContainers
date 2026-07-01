package com.hybris.plaincontainers.data.model

import com.hybris.plaincontainers.data.ItemCountZeroBehavior
import com.hybris.plaincontainers.data.SupportedLocale
import kotlinx.serialization.Serializable

/**
 * Model for the settings.json file, exposing config about:
 * - Current locale
 * - If the drag-sort switch is toggled on in ContainerOverview- and ContainerDetailsFragment
 * - Behaviour when an EntryItemInContainer count reaches 0
 */
@Serializable
data class Settings(
    val locale: SupportedLocale,
    val dragEnabled: Boolean,
    val itemCountZeroBehavior: ItemCountZeroBehavior
)