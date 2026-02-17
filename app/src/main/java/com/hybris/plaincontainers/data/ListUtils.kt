package com.hybris.plaincontainers.data

import com.hybris.plaincontainers.data.entities.EntryBase
import com.hybris.plaincontainers.views.sortpopup.SortOption
import com.hybris.plaincontainers.views.sortpopup.SortSelection

object ListUtils {

    fun <T: EntryBase> sortEntryList(items: MutableList<T>, sortParams: SortSelection) {
        when(sortParams.option) {
            SortOption.NAME -> {
                if(sortParams.isAscending) items.sortBy { e -> e.name.uppercase() }
                else items.sortByDescending { e -> e.name.uppercase() }
            }
            SortOption.DATE_ADDED -> {
                if(sortParams.isAscending) items.sortBy { e -> e.dateAdded }
                else items.sortByDescending { e -> e.dateAdded }
            }
            SortOption.DATE_MODIFIED -> {
                if(sortParams.isAscending) items.sortBy { e -> e.dateModified }
                else items.sortByDescending { e -> e.dateModified }
            }
            SortOption.CUSTOM -> {}
        }
    }
}