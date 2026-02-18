package com.hybris.plaincontainers.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hybris.plaincontainers.data.entities.EntryItem
import com.hybris.plaincontainers.data.repositories.EntryItemRepository
import kotlinx.coroutines.launch

class AddItemViewModel(containerId: Long) : ViewModel() {
    private val itemRepository = EntryItemRepository(containerId)

    fun insertToContainer(item: EntryItem) {
        viewModelScope.launch {
            itemRepository.insertInContainer(item)
        }
    }

}