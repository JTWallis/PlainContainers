package com.hybris.plaincontainers.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BarcodeMetadata(
    val productName: String,
    val productDescription: String
) {}