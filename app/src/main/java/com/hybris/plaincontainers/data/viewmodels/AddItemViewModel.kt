package com.hybris.plaincontainers.data.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hybris.plaincontainers.data.HttpManager
import com.hybris.plaincontainers.data.entities.EntryItem
import com.hybris.plaincontainers.data.model.BarcodeMetadata
import com.hybris.plaincontainers.data.repositories.EntryItemRepository
import kotlinx.coroutines.launch

class AddItemViewModel(containerId: Long) : ViewModel() {
    private val itemRepository = EntryItemRepository(containerId)

    fun insertToContainer(item: EntryItem) {
        viewModelScope.launch {
            itemRepository.insertInContainer(item)
        }
    }

    fun fetchBarcodeMetadata(
        barcode: String,
        successCallback: (metadata: BarcodeMetadata) -> Unit,
        failCallback: (error: String) -> Unit
    ) {
        viewModelScope.launch {
            HttpManager.fetchBarcodeMetadata(barcode)
                .onSuccess { metadata ->
                    if(metadata.productName.isEmpty()) {
                        failCallback("Unknown error")
                    } else if(metadata.productName == "error") {
                        failCallback(metadata.productDescription)
                    } else {
                        successCallback(metadata)
                    }
                }
                .onFailure { e ->
                    Log.w("AddItemViewModel", "fetchBarcodeMetadata: Fetch metadata failure ${e.message}")
                    failCallback("Could not fetch")
                }
        }
    }

    fun fetchBarcodeThumbnail(
        context: Context,
        barcode: String,
        successCallback: (uri: Uri) -> Unit,
        failCallback: (error: String) -> Unit
    ) {
        viewModelScope.launch {
            HttpManager.fetchBarcodeThumbnail(context, barcode)
                .onSuccess { uri -> successCallback(uri) }
                .onFailure { e ->
                    Log.w("AddItemViewModel", "fetchBarcodeThumbnail: Fetch thumbnail failure ${e.message}")
                    failCallback("Could not fetch thumbnail")
                }
        }
    }
}