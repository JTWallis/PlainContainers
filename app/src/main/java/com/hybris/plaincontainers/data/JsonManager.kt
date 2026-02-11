package com.hybris.plaincontainers.data

import android.content.Context
import android.util.Log
import com.hybris.plaincontainers.data.model.EntryContainer
import com.hybris.plaincontainers.data.model.EntryItem
import com.hybris.plaincontainers.data.model.RootContainer
import com.hybris.plaincontainers.data.model.Settings
import com.hybris.plaincontainers.views.sortpopup.SortOption
import com.hybris.plaincontainers.views.sortpopup.SortSelection
import kotlinx.serialization.json.Json
import java.io.File

object JsonManager {

    private var isInit = false
    private val FILENAME = "containers.json"
    private const val FILENAME_SETTINGS = "settings.json"
    private var PATH = ""
    private var PATH_SETTINGS = ""
    private val json = Json{prettyPrint = true}
    private val jsonSettings = Json{ prettyPrint = true }
    private lateinit var root: RootContainer

    fun init(context: Context) {
        val rootPath = FileUtils.getRootPath(context)
        PATH = FileUtils.getRootPath(context) + "/$FILENAME"
        PATH_SETTINGS = "${rootPath}/${FILENAME_SETTINGS}"

        val rootNullable = readRoot()
        if(rootNullable == null) {
            root = RootContainer(
                SortSelection(SortOption.CUSTOM, true),
                emptyList()
            )
            writeRoot(root)
        } else {
            root = rootNullable
        }

        val settingsNullable = readSettings()
        SettingsManager.init(settingsNullable)
        if(settingsNullable == null) {
            writeSettings(SettingsManager.getSettings())
        }

        isInit = true
    }


    fun readSettings(): Settings? {
        try {
            val file = File(PATH_SETTINGS)
            return Json.decodeFromString(file.readText())
        } catch(e: Exception) {
            Log.d("ERROR", "Exception when reading from file $FILENAME: $e")
        }

        return null
    }

    fun writeSettings(settings: Settings) {
        try {
            val f = File(PATH_SETTINGS)
            f.writeText(jsonSettings.encodeToString(settings))
        } catch(e: Exception) {
            Log.d("ERROR", "Exception when writing to file $FILENAME: $e")
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
        ListUtils.sortEntryList(newContainers, root.sortParams)

        writeContainers(newContainers)
    }

    fun addContainer(container: EntryContainer) {
        verifyRoot()

        val newContainers = root.containers.toMutableList()
        newContainers.add(container)
        ListUtils.sortEntryList(newContainers, root.sortParams)

        writeContainers(newContainers)
    }

    fun removeContainer(containerPos: Int) {
        verifyRoot()

        val newContainers = root.containers.toMutableList()
        newContainers.removeAt(containerPos)

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
            container.description,
            container.dateAdded,
            container.dateModified,
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

    fun writeItem(containerPos: Int, itemPos: Int, entryItem: EntryItem) {
        verifyRoot()

        val container = root.containers[containerPos]
        val newItems = container.items.toMutableList()
        newItems[itemPos] = entryItem
        ListUtils.sortEntryList(newItems, container.sortParams)

        writeItems(containerPos, newItems)
    }

    fun addItem(containerPos: Int, entryItem: EntryItem) {
        verifyRoot()

        val container = root.containers[containerPos]
        val newItems = container.items.toMutableList()
        newItems.add(entryItem)
        ListUtils.sortEntryList(newItems, container.sortParams)

        writeItems(containerPos, newItems)
    }

    fun removeItem(containerPos: Int, itemPos: Int) {
        verifyRoot()

        val newItems = root.containers[containerPos].items.toMutableList()
        newItems.removeAt(itemPos)
        writeItems(containerPos, newItems)
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