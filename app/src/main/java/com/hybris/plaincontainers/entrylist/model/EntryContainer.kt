package com.hybris.plaincontainers.entrylist.model
import kotlinx.serialization.Serializable

@Serializable
data class EntryContainer(
    override val name: String,
    override val thumbnailSrc: String,
    val color: String,
    val items: List<EntryItem>
) : EntryBase() {

}