package com.hybris.plaincontainers.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.hybris.plaincontainers.data.entities.EntryContainer
import kotlinx.coroutines.flow.Flow

@Dao
abstract class EntryContainerDao: EntryBaseDao<EntryContainer> {

    @Query("SELECT * FROM entry_containers WHERE containerId = :containerId")
    abstract fun getById(containerId: Long): Flow<EntryContainer?>

    @Query("SELECT * FROM entry_containers")
    abstract fun getAll(): Flow<List<EntryContainer>>

    @Query("""
        SELECT * FROM entry_containers
        ORDER BY name ASC
    """)
    abstract fun getAllOrderByNameAsc(): Flow<List<EntryContainer>>

    @Query("""
        SELECT * FROM entry_containers
        ORDER BY name DESC
    """)
    abstract fun getAllOrderByNameDesc(): Flow<List<EntryContainer>>

    @Query("""
        SELECT * FROM entry_containers
        ORDER BY dateAdded ASC
    """)
    abstract fun getAllOrderByDateAddedAsc(): Flow<List<EntryContainer>>

    @Query("""
        SELECT * FROM entry_containers
        ORDER BY dateAdded DESC
    """)
    abstract fun getAllOrderByDateAddedDesc(): Flow<List<EntryContainer>>

    @Query("""
        SELECT * FROM entry_containers
        ORDER BY dateModified ASC
    """)
    abstract fun getAllOrderByDateModifiedAsc(): Flow<List<EntryContainer>>

    @Query("""
        SELECT * FROM entry_containers
        ORDER BY dateModified DESC
    """)
    abstract fun getAllOrderByDateModifiedDesc(): Flow<List<EntryContainer>>

    @Query("""
        SELECT * FROM entry_containers
        ORDER BY sortPosition ASC
    """)
    abstract fun getAllOrderBySortPos(): Flow<List<EntryContainer>>

    @Query("""
        UPDATE entry_containers
        SET sortOption = :sortOptionOrdinal, sortAscending = :isAscending 
        WHERE containerId = :containerId
    """)
    abstract suspend fun updateSortParams(containerId: Long, sortOptionOrdinal: Int, isAscending: Boolean)

    @Query("""
        SELECT COUNT(*) FROM entry_containers
    """)
    abstract suspend fun count(): Int

}