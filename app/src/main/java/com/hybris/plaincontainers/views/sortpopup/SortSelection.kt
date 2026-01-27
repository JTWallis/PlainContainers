package com.hybris.plaincontainers.views.sortpopup

import kotlinx.serialization.Serializable

@Serializable
class SortSelection(
    var option: SortOption,
    var isAscending: Boolean
) {}