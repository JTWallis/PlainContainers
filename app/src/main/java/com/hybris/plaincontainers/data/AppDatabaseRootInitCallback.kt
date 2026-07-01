package com.hybris.plaincontainers.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hybris.plaincontainers.data.entities.Root
import com.hybris.plaincontainers.views.sortpopup.SortOption

/**
 * Custom RoomDatabase.Callback that initializes default values for the Root table,
 * when the database is created for the first time.
 * This is necessary, as otherwise ContainerOverviewFragment would try to fetch the SortingSelection
 * of the Root and fail, as its values don't exist yet.
 */
class AppDatabaseRootInitCallback: RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val rootDefault = Root(
            1,
            SortOption.DATE_ADDED.ordinal,
            true
        )

        val values = ContentValues().apply {
            put("rootId", rootDefault.rootId)
            put("sortOption", rootDefault.sortOption)
            put("sortAscending", rootDefault.sortAscending)
        }

        db.insert("root", SQLiteDatabase.CONFLICT_IGNORE, values)
    }
}