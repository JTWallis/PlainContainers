package com.hybris.plaincontainers.data

import android.content.Context
import android.util.Log
import com.hybris.plaincontainers.data.model.Settings
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Manager for reading and writing certain models as JSON files.
 * Should only be used by certain other managers (currently only SettingsManager).
 * Must call init before use!
 */
object JsonManager {

    private var isInit = false
    private const val FILENAME_SETTINGS = "settings.json"
    private var PATH_SETTINGS = ""
    private val jsonSettings = Json{ prettyPrint = true }

    fun init(context: Context) {
        isInit = true

        val rootPath = FileUtils.getRootPath(context)
        PATH_SETTINGS = "${rootPath}/${FILENAME_SETTINGS}"

        val settingsNullable = readSettings()
        SettingsManager.init(settingsNullable)
        if(settingsNullable == null) {
            writeSettings(SettingsManager.getSettings())
        }
    }


    fun readSettings(): Settings? {
        verifyInit()
        try {
            val file = File(PATH_SETTINGS)
            return Json.decodeFromString(file.readText())
        } catch(e: Exception) {
            Log.e("JsonManager", "readSettings: Exception when reading from file $FILENAME_SETTINGS: $e")
        }

        return null
    }

    fun writeSettings(settings: Settings) {
        verifyInit()
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

    private fun verifyInit() {
        if(!isInit) throwExceptionInit()
    }

}