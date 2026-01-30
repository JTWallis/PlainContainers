package com.hybris.plaincontainers.data.model
import com.hybris.plaincontainers.views.sortpopup.SortSelection
import kotlinx.serialization.Serializable

@Serializable
data class EntryContainer(
    override val name: String,
    override val thumbnailSrc: String,
    val color: String,
    val sortParams: SortSelection,
    val items: List<EntryItem>
) : EntryBase(), java.io.Serializable {
    var isExpanded: Boolean = false
}