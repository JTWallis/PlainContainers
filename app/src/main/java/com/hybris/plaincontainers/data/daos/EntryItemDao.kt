package com.hybris.plaincontainers.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.hybris.plaincontainers.data.AppDatabaseManager
import com.hybris.plaincontainers.data.entities.EntryContainerItemCrossRef
import com.hybris.plaincontainers.data.entities.EntryItem
import com.hybris.plaincontainers.data.entities.EntryItemInContainer
import kotlinx.coroutines.flow.Flow

private const val QUERY_SELECT_JOIN = """
        SELECT 
            i.*,
            cr.amount,
            cr.sortPosition
        FROM entry_items i
        LEFT JOIN entry_containers_items_cross_ref cr
        ON i.itemId = cr.itemId
        WHERE cr.containerId = :containerId
    """

@Dao
abstract class EntryItemDao: EntryBaseDao<EntryItem> {


    @Query("SELECT * FROM entry_items")
    abstract fun getAll(): Flow<List<EntryItem>>

    @Query(QUERY_SELECT_JOIN)
    abstract fun getAllInContainer(containerId: Long): Flow<List<EntryItemInContainer>>

    @Query("""
        $QUERY_SELECT_JOIN
        ORDER BY i.name ASC
    """)
    abstract fun getAllInContainerOrderByNameAsc(containerId: Long): Flow<List<EntryItemInContainer>>

    @Query("""
        $QUERY_SELECT_JOIN
        ORDER BY i.name DESC
    """)
    abstract fun getAllInContainerOrderByNameDesc(containerId: Long): Flow<List<EntryItemInContainer>>

    @Query("""
        $QUERY_SELECT_JOIN
        ORDER BY i.dateAdded ASC
    """)
    abstract fun getAllInContainerOrderByDateAddedAsc(containerId: Long): Flow<List<EntryItemInContainer>>

    @Query("""
        $QUERY_SELECT_JOIN
        ORDER BY i.dateAdded DESC
    """)
    abstract fun getAllInContainerOrderByDateAddedDesc(containerId: Long): Flow<List<EntryItemInContainer>>

    @Query("""
        $QUERY_SELECT_JOIN
        ORDER BY i.dateModified ASC
    """)
    abstract fun getAllInContainerOrderByDateModifiedAsc(containerId: Long): Flow<List<EntryItemInContainer>>

    @Query("""
        $QUERY_SELECT_JOIN
        ORDER BY i.dateModified DESC
    """)
    abstract fun getAllInContainerOrderByDateModifiedDesc(containerId: Long): Flow<List<EntryItemInContainer>>

    @Query("""
        $QUERY_SELECT_JOIN
        ORDER BY cr.sortPosition ASC
    """)
    abstract fun getAllInContainerOrderBySortPos(containerId: Long): Flow<List<EntryItemInContainer>>

    @Query("SELECT * FROM entry_items WHERE itemId = :itemId")
    abstract fun getById(itemId: Long): Flow<EntryItem>

    @Transaction
    open suspend fun addToContainer(containerId: Long, itemId: Long) {
        val crossRef = EntryContainerItemCrossRef(containerId, itemId, 0, 1)
        AppDatabaseManager.get().containerItemCrossRefDao().insert(crossRef)
    }

    @Transaction
    open suspend fun insertToContainer(item: EntryItem, containerId: Long) {
        val insertedId = insert(item)
        val crossRef = EntryContainerItemCrossRef(containerId, insertedId, 1, 1)
        AppDatabaseManager.get().containerItemCrossRefDao().insert(crossRef)
    }

    @Transaction
    open suspend fun updateInContainer(containerId: Long, vararg item: EntryItemInContainer) {
        val crossRefs = item.map { e ->
            EntryContainerItemCrossRef(
                containerId,
                e.item.itemId,
                e.amount,
                e.sortPosition
            )
        }.toTypedArray()

        AppDatabaseManager.get().containerItemCrossRefDao().update(*crossRefs)
    }

    @Transaction
    open suspend fun updateAmountInContainer(containerId: Long, itemId: Long, newAmount: Int) {
        AppDatabaseManager.get().containerItemCrossRefDao().updateAmount(containerId, itemId, newAmount)
    }
}
