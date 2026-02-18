package com.hybris.plaincontainers.data.entities

import androidx.room.Embedded
import androidx.room.Ignore

data class EntryItemInContainer(
    @Embedded val item: EntryItem,
    val amount: Int,
    val sortPosition: Int
): EntryBase() {
    @Ignore override val internalId: Long = item.internalId
    @Ignore override val name: String = item.name
    @Ignore override val thumbnailSrc: String = item.thumbnailSrc
    @Ignore override val description: String = item.description
    @Ignore override val dateAdded: Int = item.dateAdded
    @Ignore override val dateModified: Int = item.dateModified
}