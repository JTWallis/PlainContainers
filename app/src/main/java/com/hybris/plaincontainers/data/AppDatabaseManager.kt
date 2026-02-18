package com.hybris.plaincontainers.data

import android.content.Context
import android.util.Log
import androidx.room.Room

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
            Log.e("ERROR", "Exception when building AppDatabase: $e")
        }


        isInit = true
    }

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