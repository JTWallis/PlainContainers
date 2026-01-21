package com.hybris.plaincontainers.data.model
import kotlinx.serialization.Serializable

@Serializable
data class EntryContainer(
    override val name: String,
    override val thumbnailSrc: String,
    val color: String,
    val items: List<EntryItem>
) : EntryBase() {
}