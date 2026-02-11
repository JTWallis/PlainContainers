package com.hybris.plaincontainers.data.model

import kotlinx.serialization.Serializable

@Serializable
data class EntryItem(
    override val name: String,
    override val thumbnailSrc: String,
    override val description: String,
    override val dateAdded: Int,
    override val dateModified: Int,
    val amount: Int
) : EntryBase() {

}