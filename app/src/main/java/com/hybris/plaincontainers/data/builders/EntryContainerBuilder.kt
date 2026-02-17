package com.hybris.plaincontainers.data.builders

import com.hybris.plaincontainers.data.entities.EntryContainer
import com.hybris.plaincontainers.views.sortpopup.SortOption

object EntryContainerBuilder {

    fun from(
        other: EntryContainer,
        name: String = other.name,
        thumbnailSrc: String? = other.thumbnailSrc,
        description: String? = other.description,
        dateAdded: Int = other.dateAdded,
        dateModified: Int = other.dateModified,
        color: Int? = other.color,
        sortOption: Int = other.sortOption,
        sortAscending: Boolean = other.sortAscending,
        sortPosition: Int = other.sortPosition
    ): EntryContainer {

        return EntryContainer(
            other.containerId,
            name,
            thumbnailSrc,
            description,
            dateAdded,
            dateModified,
            color,
            sortOption,
            sortAscending,
            sortPosition
        )
    }

    fun empty(): EntryContainer {
        return EntryContainer(
            0,
            "",
            "",
            "",
            0,
            0,
            0,
            SortOption.DATE_ADDED.ordinal,
            true,
            0
        )
    }
}