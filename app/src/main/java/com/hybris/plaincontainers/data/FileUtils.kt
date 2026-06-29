package com.hybris.plaincontainers.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.net.toFile
import androidx.core.net.toUri
import java.io.File

object FileUtils {

    private lateinit var context_: Context

    fun init(context: Context) {
        context_ = context
    }

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

    fun createScaledPhoto(context: Context, uri: Uri): Uri {
        val input = context.contentResolver.openInputStream(uri)!!
        val bitmap = BitmapFactory.decodeStream(input)
        return createScaledPhoto(context, bitmap)
    }

    fun createScaledPhoto(context: Context, bitmap: Bitmap): Uri {
        val scaledBitmap = scaleBitmap(bitmap)
        return createPhotoFromBitmap(context, scaledBitmap)
    }

    fun createPhotoFromBitmap(context: Context, bitmap: Bitmap): Uri {
        val file = File(
            getPhotosPath(context),
            "photo_${System.currentTimeMillis()}_${bitmap.width}x${bitmap.height}"
        )

        file.outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, it)
        }

        return file.toUri()
    }

    fun scaleBitmap(bitmap: Bitmap): Bitmap {
        val maxVal = 256
        var width = bitmap.width
        var height = bitmap.height

        // Scale bigger dimension down to maxVal and keep aspect ratio.
        if(width > maxVal || height > maxVal) {
            val biggerDimension = width.coerceAtLeast(height)
            val scaleFactor = biggerDimension.toFloat() / maxVal.toFloat()
            width = (width / scaleFactor).toInt()
            height = (height / scaleFactor).toInt()
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }

    fun storeBarcodeThumbnail(barcode: String, bytes: ByteArray): Uri {
        val file = File(getPhotosPath(context_), "barcode_${barcode}.jpg")
        if(!file.exists()) {
            file.writeBytes(bytes)
        }

        return file.toUri()
    }

    fun isValidUri(uri: Uri?): Boolean {
        if(uri == null || uri == Uri.EMPTY) return false

        try {
            val file = uri.toFile()
            return file.exists()
        } catch(e: IllegalArgumentException) {
            return false
        }
    }
}