package com.hybris.plaincontainers.data.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "entry_containers")
data class EntryContainer(
    @PrimaryKey(autoGenerate = true) val containerId: Long = 0L,
    override val name: String,
    override val thumbnailSrc: String?,
    override val description: String?,
    override val dateAdded: Int,
    override val dateModified: Int,
    val color: Int?,
    val sortOption: Int,
    val sortAscending: Boolean,
    val sortPosition: Int
): EntryBase() {
    @Ignore
    override val internalId: Long = containerId
    @Ignore
    var isExpanded: Boolean = false
}