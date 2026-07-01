package com.hybris.plaincontainers.views.sortpopup

import kotlinx.serialization.Serializable

/**
 * Represents a SortOption with a sorting-order.
 */
@Serializable
class SortSelection(
    var option: SortOption,
    var isAscending: Boolean
): java.io.Serializable