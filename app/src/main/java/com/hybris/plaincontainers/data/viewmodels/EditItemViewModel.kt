package com.hybris.plaincontainers.data.viewmodels

import androidx.lifecycle.viewModelScope
import com.hybris.plaincontainers.data.entities.EntryItem
import com.hybris.plaincontainers.data.repositories.EntryItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class EditItemViewModel(containerId: Long, itemId: Long) : MetadataBaseViewModel() {

    private val itemRepository = EntryItemRepository(containerId)

    val item = itemRepository.getById(itemId)
    override val entry: Flow<Int> = flow {
        item.collect { emit(1) }
    }

    fun update(item: EntryItem) {
        viewModelScope.launch {
            itemRepository.update(item)
        }
    }

    fun delete(item: EntryItem) {
        viewModelScope.launch {
            itemRepository.delete(item)
        }
    }
}