package com.hybris.plaincontainers.data

import android.content.Context
import android.util.Log
import com.hybris.plaincontainers.data.model.EntryContainer
import com.hybris.plaincontainers.data.model.EntryItem
import com.hybris.plaincontainers.data.model.RootContainer
import com.hybris.plaincontainers.data.states.EntryStateContainer
import kotlinx.serialization.json.Json
import java.io.File

class JsonManager(context: Context) {

    private val FILENAME = "containers.json"
    private val PATH = context.filesDir.path + "/$FILENAME"
    private val json = Json{prettyPrint = true}

    fun readRoot(): RootContainer? {
        try {
            val file = File(PATH)
            return Json.decodeFromString(file.readText())
        } catch(e: Exception) {
            Log.d("ERROR", "Exception when reading from file $FILENAME: $e")
        }

        return null
    }

    fun writeRoot(rootContainer: RootContainer) {
        try {
            val f = File(PATH)
            f.writeText(json.encodeToString(rootContainer))
        } catch(e: Exception) {
            Log.d("ERROR", "Exception when writing to file $FILENAME: $e")
        }
    }

    fun readContainers(): MutableList<EntryContainer> {
        val root = readRoot() ?: return ArrayList()
        return root.containers.toMutableList()
    }

    fun writeContainers(containers: MutableList<EntryContainer>) {
        val root = readRoot() ?: return
        val models = containers.map{ e -> e.model}
        root.containers = models

        writeRoot(root)
    }

}