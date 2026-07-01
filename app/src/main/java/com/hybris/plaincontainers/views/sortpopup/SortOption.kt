package com.hybris.plaincontainers.views.sortpopup

import androidx.annotation.StringRes
import com.hybris.plaincontainers.R

/**
 * Represents a supported option to sort a list by.
 * Every entry is also tied to a string-resource for localization.
 */
enum class SortOption(@get:StringRes val sortLabelId: Int) {
    NAME            (R.string.popup_sort_entry_name),
    DATE_ADDED      (R.string.popup_sort_entry_date_added),
    DATE_MODIFIED   (R.string.popup_sort_entry_date_modified),
    CUSTOM          (R.string.popup_sort_entry_custom)
}