package com.hybris.plaincontainers.entrylist.model

import kotlinx.serialization.Serializable

@Serializable
data class EntryItem(
    override val name: String,
    override val thumbnailSrc: String,
    val amount: Integer
) : EntryBase() {

}