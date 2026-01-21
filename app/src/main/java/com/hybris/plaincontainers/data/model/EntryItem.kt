package com.hybris.plaincontainers.data.model

import kotlinx.serialization.Serializable

@Serializable
data class EntryItem(
    override val name: String,
    override val thumbnailSrc: String,
    val amount: Int
) : EntryBase() {

}