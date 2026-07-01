package com.hybris.plaincontainers.data.repositories

import com.hybris.plaincontainers.data.daos.EntryBaseDao
import com.hybris.plaincontainers.data.entities.EntryBase

/**
 * Base class for repositories, targeting EntryBase instances and intend to expose full CRUD operations.
 */
abstract class EntryBaseRepository<T: EntryBase, V: EntryBaseDao<T>> {
    protected abstract val dao: V

    suspend fun update(vararg obj: T) {
        dao.update(*obj)
    }

    suspend fun insert(obj: T) {
        dao.insert(obj)
    }

    suspend fun delete(obj: T) {
        dao.delete(obj)
    }
}