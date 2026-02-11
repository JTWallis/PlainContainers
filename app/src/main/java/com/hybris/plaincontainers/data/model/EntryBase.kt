package com.hybris.plaincontainers.data.model

import kotlinx.serialization.Serializable

@Serializable
abstract class EntryBase: java.io.Serializable {
    abstract val name: String
    abstract val thumbnailSrc: String
    abstract val description: String
    abstract val dateAdded: Int
    abstract val dateModified: Int
}