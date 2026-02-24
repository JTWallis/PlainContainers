package com.hybris.plaincontainers.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hybris.plaincontainers.data.entities.EntryContainer
import com.hybris.plaincontainers.data.repositories.EntryContainerRepository
import kotlinx.coroutines.launch

class AddContainerViewModel(): ViewModel() {
    private val containerRepository = EntryContainerRepository()

    fun insertContainer(container: EntryContainer) {
        viewModelScope.launch {
            containerRepository.insertSorted(container)
        }
    }

}