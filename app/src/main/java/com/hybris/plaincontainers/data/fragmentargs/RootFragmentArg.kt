package com.hybris.plaincontainers.data.fragmentargs

import com.hybris.plaincontainers.data.model.EntryBase
import com.hybris.plaincontainers.views.sortpopup.SortSelection
import java.io.Serializable

open class RootFragmentArg(
    open val listItems: MutableList<EntryBase>,
    open val sortParams: SortSelection
): Serializable {}