package com.hybris.plaincontainers.data.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "entry_items")
data class EntryItem(
    @PrimaryKey(autoGenerate = true) val itemId: Long = 0L,
    override val name: String,
    override val thumbnailSrc: String,
    override val description: String,
    override val dateAdded: Int,
    override val dateModified: Int
): EntryBase() {
    @Ignore
    override val internalId: Long = itemId
}