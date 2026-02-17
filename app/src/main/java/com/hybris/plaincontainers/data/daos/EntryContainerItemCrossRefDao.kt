package com.hybris.plaincontainers.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hybris.plaincontainers.data.entities.EntryContainerItemCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryContainerItemCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crossRef: EntryContainerItemCrossRef)

    @Transaction
    @Query("""
        SELECT * FROM entry_containers_items_cross_ref
        WHERE containerId = :containerId
        AND itemId = :itemId
    """)
    fun getItemWithContainer(containerId: Long, itemId: Long): Flow<EntryContainerItemCrossRef?>

    @Update
    suspend fun update(vararg crossRef: EntryContainerItemCrossRef)

    @Query("""
        UPDATE entry_containers_items_cross_ref
        SET amount = :amount
        WHERE containerId = :containerId
        AND itemId = :itemId
    """)
    suspend fun updateAmount(containerId: Long, itemId: Long, amount: Int)

    @Query("""
        DELETE FROM entry_containers_items_cross_ref
        WHERE containerId = :containerId
        AND itemId = :itemId
    """)
    suspend fun delete(containerId: Long, itemId: Long)

    @Query("""
        SELECT COUNT(*) FROM entry_containers_items_cross_ref
        WHERE containerId = :containerId
    """)
    suspend fun countItems(containerId: Long): Int
}