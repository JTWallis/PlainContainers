package com.hybris.plaincontainers.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hybris.plaincontainers.data.entities.EntryItemInContainer
import com.hybris.plaincontainers.data.repositories.EntryItemRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for EntryDragExpandViewHolder,
 * specifically for the nested RecyclerView of ContainerOverviewFragment.
 * Exposes all EntryItemInContainer within an EntryContainer.
 */
class DragExpandViewModel(val containerId: Long): ViewModel() {

    private val itemRepository = EntryItemRepository(containerId)

    val items: StateFlow<List<EntryItemInContainer>> =
        itemRepository.itemsInContainerOrdered
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

}