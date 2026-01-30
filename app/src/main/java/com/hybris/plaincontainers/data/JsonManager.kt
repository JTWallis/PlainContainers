package com.hybris.plaincontainers.data

import android.content.Context
import android.util.Log
import com.hybris.plaincontainers.data.model.EntryContainer
import com.hybris.plaincontainers.data.model.EntryItem
import com.hybris.plaincontainers.data.model.RootContainer
import kotlinx.serialization.json.Json
import java.io.File

object JsonManager {

    private val FILENAME = "containers.json"
    private var PATH = ""
    private val json = Json{prettyPrint = true}
    private lateinit var root: RootContainer

    fun init(context: Context) {
        PATH = context.filesDir.path + "/$FILENAME"

        val rootNullable = readRoot()
        if(rootNullable == null) {
            throw NullPointerException("Could not read root from JSON!")
            // TODO: What about empty/missing JSON? Instantiate new RootContainer here (and write it)
        } else {
            root = rootNullable
        }
    }

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

    fun getRoot(): RootContainer {
        verifyRoot()
        return root
    }

    fun readContainers(): MutableList<EntryContainer> {
        val root = readRoot() ?: return ArrayList()
        return root.containers.toMutableList()
    }

    fun writeContainers(containers: MutableList<EntryContainer>) {
        val root = readRoot() ?: return
        root.containers = containers

        writeRoot(root)
    }

    fun writeItems(items: MutableList<EntryItem>, containerPos: Int) {
        if(containerPos < 0) return
        val root = readRoot() ?: return
        val container = root.containers[containerPos]
        container.items = items

        writeRoot(root)
    }

}