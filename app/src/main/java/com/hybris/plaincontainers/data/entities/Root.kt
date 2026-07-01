package com.hybris.plaincontainers.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "root")
data class Root(
    @PrimaryKey(autoGenerate = true) val rootId: Long = 0L,
    val sortOption: Int,
    val sortAscending: Boolean
)