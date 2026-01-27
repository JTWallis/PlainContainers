package com.hybris.plaincontainers.data

import com.hybris.plaincontainers.data.model.EntryBase
import com.hybris.plaincontainers.views.sortpopup.SortSelection
import java.io.Serializable

class ContainerActivityPackage(
    val containerPos: Int,
    val listItems: MutableList<EntryBase>,
    val sortParams: SortSelection
): Serializable {}