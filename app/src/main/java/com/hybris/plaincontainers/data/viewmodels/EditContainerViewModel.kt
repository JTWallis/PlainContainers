package com.hybris.plaincontainers.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hybris.plaincontainers.data.entities.EntryContainer
import com.hybris.plaincontainers.data.repositories.EntryContainerRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for EditContainerFragment.
 * Exposes a single EntryContainer.
 * Additionally provides db-operations to update and delete that EntryContainer.
 */
class EditContainerViewModel(val containerId: Long) : ViewModel() {
    private val containerRepository = EntryContainerRepository()

    val container = containerRepository.get(containerId)

    fun updateContainer(container: EntryContainer) {
        viewModelScope.launch {
            containerRepository.update(container)
        }
    }

    fun deleteContainer(container: EntryContainer) {
        viewModelScope.launch {
            containerRepository.delete(container)
        }
    }
}