package com.hybris.plaincontainers.data.model

import kotlinx.serialization.Serializable

@Serializable
abstract class EntryBase {
    abstract val name: String
    abstract val thumbnailSrc: String
}