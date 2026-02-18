package com.hybris.plaincontainers.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hybris.plaincontainers.data.entities.EntryItem
import com.hybris.plaincontainers.data.repositories.EntryItemRepository
import kotlinx.coroutines.launch

class EditItemViewModel(containerId: Long, itemId: Long) : ViewModel() {

    private val itemRepository = EntryItemRepository(containerId)

    val item = itemRepository.getById(itemId)

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