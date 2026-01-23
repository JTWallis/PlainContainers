package com.hybris.plaincontainers.data.model

import com.hybris.plaincontainers.views.sortpopup.SortOption
import kotlinx.serialization.Serializable

@Serializable
data class RootContainer(
    var selectedOption: SortOption,
    var selectedAscending: Boolean,
    var containers: List<EntryContainer>
) {}