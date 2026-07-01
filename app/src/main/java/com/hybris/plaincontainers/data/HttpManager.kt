package com.hybris.plaincontainers.data

import android.content.Context
import android.net.Uri
import com.hybris.plaincontainers.data.model.BarcodeMetadata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * Manager to issue certain HTTP calls.
 * Ideally the functions should be wrapped in relevant ViewModels + viewModelScope for a clean structure.
 */
object HttpManager {

    private const val URL_BASE = "http://192.168.0.2:8100/api/"

    private fun fetch(url: URL): HttpURLConnection {
        val connection = (url.openConnection() as HttpURLConnection).apply {
            connectTimeout = 10_000
            readTimeout = 10_000
            requestMethod = "GET"
        }

        val code = connection.responseCode
        if(code !in 200..299) {
            throw IOException(connection.responseMessage)
        }

        return connection
    }

    suspend fun fetchBarcodeMetadata(barcode: String): Result<BarcodeMetadata> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val url = URL("${URL_BASE}barcode/metadata/${barcode}")
                val connection = fetch(url)
                val body = connection.getInputStream().bufferedReader().readText()
                Json.decodeFromString<BarcodeMetadata>(body)
            }
        }
    }

    suspend fun fetchBarcodeThumbnail(context: Context, barcode: String): Result<Uri> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val url = URL("${URL_BASE}barcode/thumbnail/${barcode}")
                val connection = fetch(url)
                val body = connection.getInputStream().readBytes()
                FileUtils.storeBarcodeThumbnail(context, barcode, body)
            }
        }
    }

}