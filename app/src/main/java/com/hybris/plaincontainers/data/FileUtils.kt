package com.hybris.plaincontainers.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.net.toFile
import androidx.core.net.toUri
import java.io.File

object FileUtils {

    fun getRootPath(context: Context): String {
        return context.filesDir.path
    }

    fun getPhotosPath(context: Context): String {
        val photosDir = File(getRootPath(context), "photos/")
        if(!photosDir.exists()) {
            photosDir.mkdir()
        }

        return photosDir.path
    }

    fun isValidUri(uri: Uri): Boolean {
        if(uri == Uri.EMPTY) return false

        try {
            val file = uri.toFile()
            return file.exists()
        } catch(e: IllegalArgumentException) {
            return false
        }
    }
}