package com.hybris.plaincontainers.data.repositories

import com.hybris.plaincontainers.data.AppDatabaseManager
import com.hybris.plaincontainers.data.builders.EntryContainerBuilder
import com.hybris.plaincontainers.data.daos.EntryContainerDao
import com.hybris.plaincontainers.data.entities.EntryContainer
import com.hybris.plaincontainers.views.sortpopup.SortOption
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class EntryContainerRepository: EntryBaseRepository<EntryContainer, EntryContainerDao>() {
    override val dao = AppDatabaseManager.get().containerDao()
    val allContainersOrdered: Flow<List<EntryContainer>> = getAllOrdered()

    fun get(containerId: Long): Flow<EntryContainer?> {
        return dao.getById(containerId)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllOrdered(): Flow<List<EntryContainer>> {
        return AppDatabaseManager.get().rootDao().get().flatMapLatest { root ->

            return@flatMapLatest when(root.sortOption) {
                SortOption.NAME.ordinal -> {
                    if(root.sortAscending) dao.getAllOrderByNameAsc()
                    else dao.getAllOrderByNameDesc()
                }
                SortOption.DATE_ADDED.ordinal -> {
                    if(root.sortAscending) dao.getAllOrderByDateAddedAsc()
                    else dao.getAllOrderByDateAddedDesc()
                }
                SortOption.DATE_MODIFIED.ordinal -> {
                    if(root.sortAscending) dao.getAllOrderByDateModifiedAsc()
                    else dao.getAllOrderByDateModifiedDesc()
                }
                SortOption.CUSTOM.ordinal -> {
                    dao.getAllOrderBySortPos()
                }
                else -> {
                    dao.getAll()
                }
            }
        }
    }

    suspend fun insertSorted(container: EntryContainer) {

    }

    suspend fun updateSortParams(containerId: Long, sortOptionOrdinal: Int, isAscending: Boolean) {
        dao.updateSortParams(containerId, sortOptionOrdinal, isAscending)
    }

    suspend fun count(): Int {
        return dao.count()
    }
}