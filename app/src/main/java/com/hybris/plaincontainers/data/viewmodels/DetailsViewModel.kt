package com.hybris.plaincontainers.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hybris.plaincontainers.data.entities.EntryContainer
import com.hybris.plaincontainers.data.entities.EntryItemInContainer
import com.hybris.plaincontainers.data.repositories.EntryContainerRepository
import com.hybris.plaincontainers.data.repositories.EntryItemRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for ContainerDetailFragment.
 * Exposes the EntryContainer and all EntryItemInContainer within that EntryContainer.
 * Additionally provides db-operations to:
 * - Update the EntryContainer SortSelection
 * - Update any EntryItemInContainer
 * - Update only the count of any EntryItemInContainer
 * - Delete any EntryItemInContainer
 */
class DetailsViewModel(private val containerId: Long): ViewModel() {
    private val containerRepository = EntryContainerRepository()
    private val itemRepository = EntryItemRepository(containerId)

    val items: StateFlow<List<EntryItemInContainer>> =
        itemRepository.itemsInContainerOrdered
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val container: StateFlow<EntryContainer?> =
        containerRepository.get(containerId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )

    fun updateContainerSortParams(sortOptionOrdinal: Int, isAscending: Boolean) {
        viewModelScope.launch {
            containerRepository.updateSortParams(containerId, sortOptionOrdinal, isAscending)
        }
    }

    fun updateItemsInContainer(vararg items: EntryItemInContainer) {
        viewModelScope.launch {
            itemRepository.updateInContainer(*items)
        }
    }

    fun updateAmountInContainer(itemId: Long, amount: Int) {
        viewModelScope.launch {
            itemRepository.updateAmountInContainer(itemId, amount)
        }
    }

    fun deleteInContainer(item: EntryItemInContainer) {
        // For now, just delete the item itself instead of CrossRef.
        viewModelScope.launch {
            itemRepository.delete(item.item)
        }
    }
}