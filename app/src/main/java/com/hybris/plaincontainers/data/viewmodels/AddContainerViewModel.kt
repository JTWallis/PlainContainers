package com.hybris.plaincontainers.data.viewmodels

import androidx.lifecycle.viewModelScope
import com.hybris.plaincontainers.data.builders.EntryContainerBuilder
import com.hybris.plaincontainers.data.entities.EntryContainer
import com.hybris.plaincontainers.data.repositories.EntryContainerRepository
import com.hybris.plaincontainers.data.repositories.RootRepository
import com.hybris.plaincontainers.views.sortpopup.SortOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class AddContainerViewModel(): MetadataBaseViewModel() {
    private val rootRepository = RootRepository()
    private val containerRepository = EntryContainerRepository()

    // Immediately emit this inherited flow, as there is no ViewData to fill.
    override val entry: Flow<Int> = flow {
        emit(1)
    }

    fun insertContainer(container: EntryContainer) {
        viewModelScope.launch {
            // If Containers are sorted by CUSTOM, use the container count + 1 as sortPos.
            // On other sort option, sortPos is not used and updated anyway on setting to CUSTOM.
            rootRepository.root.collect { r ->
                var containerInsert = container
                if(r.sortOption == SortOption.CUSTOM.ordinal) {
                    val sortPos = containerRepository.count() + 1

                    containerInsert = EntryContainerBuilder.from(
                        container,
                        sortPosition = sortPos
                    )
                }

                containerRepository.insert(containerInsert)
            }
        }
    }

}