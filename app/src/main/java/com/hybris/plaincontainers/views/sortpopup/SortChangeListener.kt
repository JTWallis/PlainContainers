package com.hybris.plaincontainers.views.sortpopup

interface SortChangeListener {
    /**
     * Event for a change in SortOption or sort order.
     */
    fun onPopupSortOptionChanged(sortSelection: SortSelection)
}