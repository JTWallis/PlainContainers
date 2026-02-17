package com.hybris.plaincontainers.data.entities

abstract class EntryBase() {
    abstract val internalId: Long
    abstract val name: String
    abstract val thumbnailSrc: String?
    abstract val description: String?
    abstract val dateAdded: Int
    abstract val dateModified: Int
}