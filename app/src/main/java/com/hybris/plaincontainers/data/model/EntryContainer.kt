package com.hybris.plaincontainers.data.model
import com.hybris.plaincontainers.views.sortpopup.SortSelection
import kotlinx.serialization.Serializable

@Serializable
data class EntryContainer(
    override val name: String,
    override val thumbnailSrc: String,
    val color: String,
    val sortParams: SortSelection,
    var items: List<EntryItem>
) : EntryBase() {
    var isExpanded: Boolean = false
}