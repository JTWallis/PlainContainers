package com.hybris.plaincontainers.data

import com.hybris.plaincontainers.data.model.EntryBase
import com.hybris.plaincontainers.views.sortpopup.SortOption
import com.hybris.plaincontainers.views.sortpopup.SortSelection

object ListUtils {

    fun <T: EntryBase> sortEntryList(items: MutableList<T>, sortParams: SortSelection) {
        when(sortParams.option) {
            SortOption.NAME -> {
                if(sortParams.isAscending) items.sortBy { e -> e.name }
                else items.sortByDescending { e -> e.name }
            }
            SortOption.DATE_ADDED -> {}
            SortOption.DATE_MODIFIED -> {}
            SortOption.CUSTOM -> {}
        }
    }
}