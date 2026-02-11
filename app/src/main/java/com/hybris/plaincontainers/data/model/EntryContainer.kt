package com.hybris.plaincontainers.data.model
import com.hybris.plaincontainers.views.sortpopup.SortOption
import com.hybris.plaincontainers.views.sortpopup.SortSelection
import kotlinx.serialization.Serializable

@Serializable
data class EntryContainer(
    override val name: String,
    override val thumbnailSrc: String,
    override val description: String,
    override val dateAdded: Int,
    override val dateModified: Int,
    val color: Int,
    val sortParams: SortSelection = SortSelection(SortOption.CUSTOM, true),
    val items: List<EntryItem> = emptyList()
) : EntryBase(), java.io.Serializable {
    var isExpanded: Boolean = false
}