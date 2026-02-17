package com.hybris.plaincontainers.data.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface EntryBaseDao<T> {

    @Insert
    suspend fun insert(obj: T): Long

    @Update
    suspend fun update(vararg obj: T)

    @Delete
    suspend fun delete(obj: T)
}