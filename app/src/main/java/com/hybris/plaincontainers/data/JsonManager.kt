package com.hybris.plaincontainers.data

import android.content.Context
import android.util.Log
import com.hybris.plaincontainers.data.model.EntryContainer
import com.hybris.plaincontainers.data.model.EntryItem
import com.hybris.plaincontainers.data.model.RootContainer
import kotlinx.serialization.json.Json
import java.io.File

object JsonManager {

    private var isInit = false
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
        isInit = true
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

    fun writeContainers(containers: List<EntryContainer>) {
        verifyRoot()

        root = RootContainer(root.sortParams, containers)
        writeRoot(root)
    }

    fun getContainers(): List<EntryContainer> {
        verifyRoot()
        return root.containers
    }

    fun writeContainer(containerPos: Int, container: EntryContainer) {
        verifyRoot()

        val newContainers = root.containers.toMutableList()
        newContainers[containerPos] = container

        writeContainers(newContainers)
    }

    fun getContainer(containerPos: Int): EntryContainer {
        verifyRoot()
        return root.containers[containerPos]
    }

    fun writeItems(containerPos: Int, items: List<EntryItem>) {
        if(containerPos < 0) return

        val container = root.containers[containerPos]

        val newContainer = EntryContainer(
            container.name,
            container.thumbnailSrc,
            container.color,
            container.sortParams,
            items
        )

        writeContainer(containerPos, newContainer)
    }

    fun getItems(containerPos: Int): List<EntryItem> {
        verifyRoot()
        return root.containers[containerPos].items
    }

    fun getItem(containerPos: Int, itemPos: Int): EntryItem {
        verifyRoot()
        return root.containers[containerPos].items[itemPos]
    }

    private fun throwExceptionInit() {
        throw NullPointerException("Attempting to interact with JsonManager without initializing it!")
    }

    private fun throwExceptionNull() {
        throw NullPointerException("Attempting to interact with JsonManager but root is null!")
    }

    private fun verifyRoot() {
        if(!isInit) throwExceptionInit()
        if(!this::root.isInitialized) throwExceptionNull()
    }

}