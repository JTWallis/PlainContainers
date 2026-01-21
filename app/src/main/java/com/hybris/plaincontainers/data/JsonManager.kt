package com.hybris.plaincontainers.data

import android.content.Context
import android.util.Log
import com.hybris.plaincontainers.data.model.EntryContainer
import com.hybris.plaincontainers.data.states.EntryStateContainer
import kotlinx.serialization.json.Json
import java.io.File

class JsonManager(context: Context) {

    private val FILENAME = "containers.json"
    private val PATH = context.filesDir.path + "/$FILENAME"
    private val json = Json{prettyPrint = true}

    fun readContainers(): MutableList<EntryStateContainer> {
        var containers: MutableList<EntryContainer> = ArrayList()

        try {
            val file = File(PATH)
            containers = Json.decodeFromString(file.readText())
        } catch(e: Exception) {
            Log.d("ERROR", "Exception when reading from file $FILENAME: $e")
        }

        return containers.map { e -> EntryStateContainer(e) }.toMutableList()
    }

    fun writeContainers(containers: MutableList<EntryStateContainer>) {
        val models = containers.map{ e -> e.model}
        try {
            val f = File(PATH)
            f.writeText(json.encodeToString(models))
        } catch(e: Exception) {
            Log.d("ERROR", "Exception when writing to file $FILENAME: $e")
        }
    }

}