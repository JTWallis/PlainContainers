package com.hybris.plaincontainers.data.repositories

import com.hybris.plaincontainers.data.AppDatabaseManager
import com.hybris.plaincontainers.data.daos.EntryItemDao
import com.hybris.plaincontainers.data.entities.EntryItem
import com.hybris.plaincontainers.data.entities.EntryItemInContainer
import com.hybris.plaincontainers.views.sortpopup.SortOption
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class EntryItemRepository(private val containerId: Long) : EntryBaseRepository<EntryItem, EntryItemDao>() {
    override val dao: EntryItemDao = AppDatabaseManager.get().itemDao()
    private val containerDao = AppDatabaseManager.get().containerDao()
    private val crossRefDao = AppDatabaseManager.get().containerItemCrossRefDao()

    val itemsInContainerOrdered = getAllOrdered()

    fun getById(itemId: Long): Flow<EntryItem> {
        return dao.getById(itemId)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllOrdered(): Flow<List<EntryItemInContainer>> {
        return containerDao.getById(containerId).flatMapLatest { container ->
            if(container == null) {
                return@flatMapLatest dao.getAllInContainer(containerId)
            }

            return@flatMapLatest when(container.sortOption) {
                SortOption.NAME.ordinal -> {
                    if(container.sortAscending) dao.getAllInContainerOrderByNameAsc(containerId)
                    else dao.getAllInContainerOrderByNameDesc(containerId)
                }
                SortOption.DATE_ADDED.ordinal -> {
                    if(container.sortAscending) dao.getAllInContainerOrderByDateAddedAsc(containerId)
                    else dao.getAllInContainerOrderByDateAddedDesc(containerId)
                }
                SortOption.DATE_MODIFIED.ordinal -> {
                    if(container.sortAscending) dao.getAllInContainerOrderByDateModifiedAsc(containerId)
                    else dao.getAllInContainerOrderByDateModifiedDesc(containerId)
                }
                SortOption.CUSTOM.ordinal -> {
                    dao.getAllInContainerOrderBySortPos(containerId)
                }
                else -> {
                    dao.getAllInContainer(containerId)
                }
            }
        }
    }

    suspend fun insertInContainer(item: EntryItem) {
        dao.insertToContainer(item, containerId)
    }

    suspend fun updateInContainer(vararg items: EntryItemInContainer) {
        dao.updateInContainer(containerId, *items)
    }

    suspend fun updateAmountInContainer(itemId: Long, amount: Int) {
        crossRefDao.updateAmount(containerId, itemId, amount)
    }
}