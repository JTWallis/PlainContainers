package com.hybris.plaincontainers.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

@Entity(
    primaryKeys = ["containerId", "itemId"], tableName = "entry_containers_items_cross_ref",
    foreignKeys = [
        ForeignKey(
            entity = EntryContainer::class,
            parentColumns = ["containerId"],
            childColumns = ["containerId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = EntryItem::class,
            parentColumns = ["itemId"],
            childColumns = ["itemId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("containerId"), Index("itemId")]
)
data class EntryContainerItemCrossRef(
    val containerId: Long,
    val itemId: Long,
    val amount: Int,
    val sortPosition: Int
) {}