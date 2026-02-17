package com.hybris.plaincontainers.data.viewmodels

import androidx.lifecycle.viewModelScope
import com.hybris.plaincontainers.data.entities.EntryItem
import com.hybris.plaincontainers.data.repositories.EntryItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class AddItemViewModel(containerId: Long) : MetadataBaseViewModel() {
    private val itemRepository = EntryItemRepository(containerId)

    // Immediately emit this inherited flow, as there is no ViewData to fill.
    override val entry: Flow<Int> = flow {
        emit(1)
    }

    fun insertToContainer(item: EntryItem) {
        viewModelScope.launch {
            itemRepository.insertInContainer(item)
        }
    }

}