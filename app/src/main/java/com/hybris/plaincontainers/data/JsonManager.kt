package com.hybris.plaincontainers.data

import android.content.Context
import android.content.res.AssetManager
import android.util.JsonReader
import android.util.Log
import com.hybris.plaincontainers.entrylist.model.EntryContainer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream

class JsonManager(context: Context) {

    private val FILENAME = "containers.json"
    private val PATH = context.filesDir.path + "/$FILENAME"
    private val json = Json{prettyPrint = true}

    fun readContainers(): MutableList<EntryContainer> {
        var containers: MutableList<EntryContainer> = ArrayList()

        try {
            val file = File(PATH)
            containers = Json.decodeFromString(file.readText())
        } catch(e: Exception) {
            Log.d("ERROR", "Exception when reading from file $FILENAME: $e")
        }

        return containers
    }

    fun writeContainers(containers: MutableList<EntryContainer>) {
        try {
            val f = File(PATH)
            f.writeText(json.encodeToString(containers))
        } catch(e: Exception) {
            Log.d("ERROR", "Exception when writing to file $FILENAME: $e")
        }
    }

}