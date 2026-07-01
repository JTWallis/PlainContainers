package com.hybris.plaincontainers.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hybris.plaincontainers.data.entities.EntryContainer
import com.hybris.plaincontainers.data.repositories.EntryContainerRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for AddContainerFragment.
 * Provides a db-operation to insert a new EntryContainer.
 */
class AddContainerViewModel(): ViewModel() {
    private val containerRepository = EntryContainerRepository()

    fun insertContainer(container: EntryContainer) {
        viewModelScope.launch {
            containerRepository.insertSorted(container)
        }
    }

}