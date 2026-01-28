package com.hybris.plaincontainers.data.fragmentargs

import com.hybris.plaincontainers.data.model.EntryBase
import com.hybris.plaincontainers.views.sortpopup.SortSelection
import java.io.Serializable

class ContainerFragmentArg(
    override val listItems: MutableList<EntryBase>,
    override val sortParams: SortSelection,
    val listPosition: Int,
): RootFragmentArg(listItems, sortParams), Serializable {}