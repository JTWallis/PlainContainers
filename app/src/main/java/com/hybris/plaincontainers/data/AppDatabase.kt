package com.hybris.plaincontainers.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hybris.plaincontainers.data.daos.EntryContainerDao
import com.hybris.plaincontainers.data.daos.EntryContainerItemCrossRefDao
import com.hybris.plaincontainers.data.daos.EntryItemDao
import com.hybris.plaincontainers.data.daos.RootDao
import com.hybris.plaincontainers.data.entities.EntryContainer
import com.hybris.plaincontainers.data.entities.EntryContainerItemCrossRef
import com.hybris.plaincontainers.data.entities.EntryItem
import com.hybris.plaincontainers.data.entities.Root

/**
 * Room database instance that exposes all DAOs.
 * Should only be used by repositories or other DAOs.
 */
@Database(
    entities = [
        Root::class,
        EntryContainer::class,
        EntryItem::class,
        EntryContainerItemCrossRef::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun rootDao(): RootDao
    abstract fun containerDao(): EntryContainerDao
    abstract fun itemDao(): EntryItemDao
    abstract fun containerItemCrossRefDao(): EntryContainerItemCrossRefDao
}