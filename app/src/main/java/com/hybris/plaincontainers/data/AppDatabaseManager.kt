package com.hybris.plaincontainers.data

import android.content.Context
import android.util.Log
import androidx.room.Room

/**
 * Manager to get the AppDatabase instance, for interacting with DAOs.
 * Must call init before use!
 */
object AppDatabaseManager {
    private lateinit var appDatabase: AppDatabase
    private var isInit = false

    fun init(context: Context) {
        try {
            appDatabase = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "plaincontainers-database"
            )
            .addCallback(AppDatabaseRootInitCallback())
            .build()
        } catch(e: Exception) {
            Log.e("AppDatabaseManager", "init: Exception when building AppDatabase: $e")
        }


        isInit = true
    }

    /**
     * Returns the AppDatabase instance.
     * Should only be used by DAOs and repositories.
     * Must call AppDatabaseManager.init before using!
     */
    fun get(): AppDatabase {
        verifyInit()
        return appDatabase
    }

    private fun verifyInit() {
        if(!isInit || !::appDatabase.isInitialized) {
            throw NullPointerException("AppDatabaseBuilder has not been initialized!")
        }
    }
}