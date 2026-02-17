package com.hybris.plaincontainers.data.repositories

import com.hybris.plaincontainers.data.AppDatabaseManager
import com.hybris.plaincontainers.data.entities.Root
import kotlinx.coroutines.flow.Flow

class RootRepository {
    private val dao = AppDatabaseManager.get().rootDao()
    val root: Flow<Root> = dao.get()


    suspend fun updateSortParams(sortOptionOrdinal: Int, isAscending: Boolean) {
        dao.updateSortParams(sortOptionOrdinal, isAscending)
    }
}