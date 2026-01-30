package com.hybris.plaincontainers.data.model

import com.hybris.plaincontainers.views.sortpopup.SortSelection
import kotlinx.serialization.Serializable

@Serializable
data class RootContainer(
    val sortParams: SortSelection,
    val containers: List<EntryContainer>
) {}