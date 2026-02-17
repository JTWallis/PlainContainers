package com.hybris.plaincontainers.data.viewmodels

import androidx.lifecycle.viewModelScope
import com.hybris.plaincontainers.data.entities.EntryContainer
import com.hybris.plaincontainers.data.repositories.EntryContainerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class EditContainerViewModel(private val containerId: Long) : MetadataBaseViewModel() {
    private val containerRepository = EntryContainerRepository()

    val container = containerRepository.get(containerId)

    override val entry: Flow<Int> = flow {
        container.collect { emit(1) }
    }

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