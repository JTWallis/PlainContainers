package com.hybris.plaincontainers.data

import android.content.Context
import android.util.Log
import com.hybris.plaincontainers.data.model.Settings
import com.hybris.plaincontainers.views.sortpopup.SortOption
import com.hybris.plaincontainers.views.sortpopup.SortSelection
import kotlinx.serialization.json.Json
import java.io.File

object JsonManager {

    private var isInit = false
    private const val FILENAME_SETTINGS = "settings.json"
    private var PATH_SETTINGS = ""
    private val jsonSettings = Json{ prettyPrint = true }

    fun init(context: Context) {
        val rootPath = FileUtils.getRootPath(context)
        PATH_SETTINGS = "${rootPath}/${FILENAME_SETTINGS}"

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
            Log.e("JsonManager", "readSettings: Exception when reading from file $FILENAME_SETTINGS: $e")
        }

        return null
    }

    fun writeSettings(settings: Settings) {
        try {
            val f = File(PATH_SETTINGS)
            f.writeText(jsonSettings.encodeToString(settings))
        } catch(e: Exception) {
            Log.e("JsonManager", "writeSettings: Exception when writing to file $FILENAME_SETTINGS: $e")
        }
    }

    private fun throwExceptionInit() {
        throw NullPointerException("Attempting to interact with JsonManager without initializing it!")
    }

    private fun throwExceptionNull() {
        throw NullPointerException("Attempting to interact with JsonManager but root is null!")
    }

    private fun verifyRoot() {
        if(!isInit) throwExceptionInit()
    }

}