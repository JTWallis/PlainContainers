package com.hybris.plaincontainers.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hybris.plaincontainers.data.entities.EntryContainer
import com.hybris.plaincontainers.data.entities.Root
import com.hybris.plaincontainers.data.repositories.EntryContainerRepository
import com.hybris.plaincontainers.data.repositories.RootRepository
import com.hybris.plaincontainers.views.sortpopup.SortOption
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for ContainerOverviewFragment.
 * Exposes the Root model and all EntryContainers.
 * Additionally provides db-operations to update any EntryContainers and the Root SortSelection.
 */
class OverviewViewModel(): ViewModel() {

    private val rootRepository = RootRepository()
    private val containerRepository = EntryContainerRepository()

    val root: StateFlow<Root> =
        rootRepository.root
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Root(0, SortOption.DATE_ADDED.ordinal, true)
            )

    val containers: StateFlow<List<EntryContainer>> =
        containerRepository.allContainersOrdered
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun updateContainers(vararg containers: EntryContainer) {
        viewModelScope.launch {
            containerRepository.update(*containers)
        }
    }

    fun updateSortParams(sortOptionOrdinal: Int, isAscending: Boolean) {
        viewModelScope.launch {
            rootRepository.updateSortParams(sortOptionOrdinal, isAscending)
        }
    }
}