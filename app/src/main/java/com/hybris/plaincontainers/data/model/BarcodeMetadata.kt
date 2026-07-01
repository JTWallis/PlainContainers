package com.hybris.plaincontainers.data.model

import kotlinx.serialization.Serializable

/**
 * Model for deserializing a fetched barcode-metadata. Exposes info about:
 * - Product name (CAUTION: Can be empty or read "error", even on supposedly successful fetch)
 * - Product description (CAUTION: If product name reads "error", this will contain a more detailed error message )
 */
@Serializable
data class BarcodeMetadata(
    val productName: String,
    val productDescription: String
)