package com.hybris.plaincontainers.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hybris.plaincontainers.data.entities.Root
import kotlinx.coroutines.flow.Flow

@Dao
interface RootDao {
    @Query("SELECT * FROM root LIMIT 1")
    fun get(): Flow<Root>

    @Query("SELECT COUNT(1) WHERE EXISTS(SELECT * FROM root)")
    suspend fun exists(): Boolean

    @Insert
    suspend fun insert(root: Root): Long

    @Update
    suspend fun update(root: Root)

    @Query("""
        UPDATE root
        SET sortOption = :sortOptionOrdinal, sortAscending = :isAscending 
    """)
    suspend fun updateSortParams(sortOptionOrdinal: Int, isAscending: Boolean)
}